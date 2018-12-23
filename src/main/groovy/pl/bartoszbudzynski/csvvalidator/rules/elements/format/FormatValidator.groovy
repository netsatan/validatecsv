package pl.bartoszbudzynski.csvvalidator.rules.elements.format

import pl.bartoszbudzynski.csvvalidator.exceptions.ColumnContentsDiffersFromExpectedException
import pl.bartoszbudzynski.csvvalidator.exceptions.ColumnLengthViolatesConstraint

class FormatValidator {

    def regex
    Integer maxLength
    Integer minLength
    Integer exactLength

    void validateRegex(String input) {
        if(this.regex) {
            if(!input.matches(this.regex)) throw new ColumnContentsDiffersFromExpectedException()
        }
    }

    void validateLength(String input) {
        if(this.maxLength > 0) {
            if(input.size() > maxLength) throw new ColumnLengthViolatesConstraint()
        }

        if(this.exactLength > 0) {
            if(input.size() != exactLength) throw new ColumnLengthViolatesConstraint()
        }

        if(this.minLength >= 0) {
            if(input.size() < minLength) throw new ColumnLengthViolatesConstraint()
        }
    }
}
