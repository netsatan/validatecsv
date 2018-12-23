package pl.bartoszbudzynski.csvvalidator

import com.xlson.groovycsv.CsvParser
import com.xlson.groovycsv.PropertyMapper
import pl.bartoszbudzynski.csvvalidator.exceptions.CsvDatatypeValidationException
import pl.bartoszbudzynski.csvvalidator.rules.ValidationRules
import pl.bartoszbudzynski.csvvalidator.rules.elements.datatype.constants.DataType
import pl.bartoszbudzynski.csvvalidator.rules.elements.datatype.DataTypeValidator
import pl.bartoszbudzynski.csvvalidator.rules.elements.format.FormatValidator

/**
 @author Bartosz Budzyński
 @version 0.0.1
 @since 0.0.1

  Main class to process CSV file using CsvParser and validating it against set of rules defined by the user
 */
class CsvValidator {

    private CsvParser parser = new CsvParser()
    private ValidationRules rules

    Iterator iterator

    /**
     *
     * @param csv A CSV file contents as a string
     * @param rules Instance of {@link ValidationRules}
     */
    CsvValidator(String csv, ValidationRules rules) {
        this.iterator = parser.parse(csv)
        this.rules = rules
    }

    void validate() {
        iterator.eachWithIndex {
            line, lineIndex ->
                line.each {
                    PropertyMapper column ->
                        column.values.eachWithIndex {
                            String value, int columnIndex ->
                                def classNames
                                if (rules.column[columnIndex] instanceof Collection) {
                                    classNames = getClassNamesForMultipleRulesForColumn(columnIndex)
                                } else classNames = rules.column[columnIndex]?.class

                                validateDataTypes(lineIndex, columnIndex, value, classNames)
                                validateFormat(lineIndex, columnIndex, value, classNames)

                        }
                }
        }
    }

    private void validateDataTypes(int lineIndex, int columnIndex, String value, classNames) {
        if (classNames == DataType || (classNames instanceof Collection && classNames.contains(DataType.class))) {
            boolean isLineValid = DataTypeValidator.validateDataType(value, columnIndex, this.rules)
            if (!isLineValid) {
                throw new CsvDatatypeValidationException(
                        "Got unexpected datatype at line $lineIndex, column $columnIndex"
                )
            }
        }
    }

    private void validateFormat(int lineIndex, int columnIndex, String value, classNames) {
        if (classNames == FormatValidator.class) {
            rules.column[columnIndex]
            FormatValidator validator = rules.column[columnIndex] as FormatValidator
            validator.validateLength(value)
            validator.validateRegex(value)
        }

        if((classNames instanceof Collection && classNames.contains(FormatValidator.class))){
            Collection listOfRules = rules.column[columnIndex] as Collection
            listOfRules.findAll{
                rule ->
                    rule instanceof FormatValidator
            }.each {
                rule ->
                    rule.validateLength(value)
                    rule.validateRegex(value)
            }
        }

    }

    private List<Class> getClassNamesForMultipleRulesForColumn(int columnIndex) {
        List rulesForColumn = (Collection) rules.column[columnIndex]
        List classNames = []
        rulesForColumn.each {
            classNames << it.class
        }
        return classNames
    }
}
