package pl.bartoszbudzynski.csvvalidator.datatype

import pl.bartoszbudzynski.csvvalidator.CsvValidator
import pl.bartoszbudzynski.csvvalidator.exceptions.CsvDatatypeValidationException
import pl.bartoszbudzynski.csvvalidator.rules.ValidationRules
import pl.bartoszbudzynski.csvvalidator.rules.elements.datatype.constants.DataType
import spock.lang.Specification

class DataTypesSpec extends Specification {

    def "Exception is thrown when datatypes are incorrect"() {

        given:
        String csv = "ID,Name,Ratio\n" +
                     "1,John,2.231"

        def rules = new ValidationRules()

        rules.with {
            column[0] = DataType.INT
            column[1] = DataType.STRING

            "INT is incorrect here and should cause the exception"
            column[2] = DataType.INT
        }

        when:
        CsvValidator validator = new CsvValidator(csv, rules)
        validator.validate()


        then:
        thrown(CsvDatatypeValidationException)

    }

    def "No exception is thrown with correct datatypes"() {

        given:
        String csv = "ID,Name,Ratio\n" +
                "1,John,2.231"

        def rules = new ValidationRules()

        rules.with {
            "All three types are correct now"
            column[0] = DataType.INT
            column[1] = DataType.STRING
            column[2] = DataType.FLOAT
        }

        when:
        CsvValidator validator = new CsvValidator(csv, rules)
        validator.validate()


        then:
        "...and should be validated as such"
        notThrown(CsvDatatypeValidationException)
    }

}