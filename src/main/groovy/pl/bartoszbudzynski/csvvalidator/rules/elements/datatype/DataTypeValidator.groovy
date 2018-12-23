package pl.bartoszbudzynski.csvvalidator.rules.elements.datatype

import pl.bartoszbudzynski.csvvalidator.exceptions.UnexpectedValidationRule
import pl.bartoszbudzynski.csvvalidator.rules.ValidationRules
import pl.bartoszbudzynski.csvvalidator.rules.elements.datatype.constants.DataType

class DataTypeValidator {

    static boolean validateDataType (String input, int columnIndex, ValidationRules rules) {
        def currentRules = rules.column[columnIndex]
        DataType dataTypeToCheck

        if(currentRules instanceof Collection) {
            currentRules.each {
                rule ->
                    if(rule instanceof DataType) dataTypeToCheck = rule
                    else return
            }
        } else if (currentRules instanceof DataType) dataTypeToCheck = currentRules
        else throw new UnexpectedValidationRule()

        switch(dataTypeToCheck) {
            case DataType.STRING:
                if(!input.isNumber()) return true
                break

            case DataType.INT:
                if(input.isInteger()) return true
                break

            case DataType.FLOAT:
                if(input.isBigDecimal()) return true
                break

            default:
                return false

        }
    }

}
