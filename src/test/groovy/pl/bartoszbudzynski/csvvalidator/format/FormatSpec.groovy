package pl.bartoszbudzynski.csvvalidator.format

import pl.bartoszbudzynski.csvvalidator.CsvValidator
import pl.bartoszbudzynski.csvvalidator.exceptions.ColumnContentsDiffersFromExpectedException
import pl.bartoszbudzynski.csvvalidator.exceptions.ColumnLengthViolatesConstraint
import pl.bartoszbudzynski.csvvalidator.rules.ValidationRules
import pl.bartoszbudzynski.csvvalidator.rules.elements.format.Format
import spock.lang.Specification

class FormatSpec extends Specification {

    def "Throw exception if column contents doesn't match the regex"() {
        given:
        String csv = "ID,Name,Ratio\n" +
                "1,John,2.231"

        def rules = new ValidationRules()

        "Column with index 1 (Name) doesn't match the regex - only three letters should be there"
        rules.with {
            column[1] = Format.of("\\w{3}")
        }

        when:
        "Validation is performed"
        CsvValidator validator = new CsvValidator(csv, rules)
        validator.validate()


        then:
        "ColumnContentsDiffersFromExpectedException is thrown"
        thrown(ColumnContentsDiffersFromExpectedException)
    }

    def "Throw exception if length differs from given value"() {
        given:
        String csv = "ID,Name,Ratio\n" +
                "1,John,2.231"

        def rules = new ValidationRules()

        "Set up the rule - column with index 1 ('Name') should be exactly 2 characters long"
        rules.with {
            column[1] = Format.length(2)
        }

        when:
        "Validation is performed"
        CsvValidator validator = new CsvValidator(csv, rules)
        validator.validate()


        then:
        "ColumnLengthViolatesConstraint is thrown"
        thrown(ColumnLengthViolatesConstraint)
    }

    def "Throw exception if length exceeds given maximum"() {
        given:
        String csv = "ID,Name,Ratio\n" +
                "1,John,2.231"

        def rules = new ValidationRules()

        "Set up the rule - column with index 1 ('Name') should be no longer than 2 characters"
        rules.with {
            column[1] = Format.length(max: 2)
        }

        when:
        "Validation is performed"
        CsvValidator validator = new CsvValidator(csv, rules)
        validator.validate()


        then:
        "ColumnLengthViolatesConstraint is thrown"
        thrown(ColumnLengthViolatesConstraint)
    }

    def "Throw exception if length is below given minimum"() {
        given:
        String csv = "ID,Name,Ratio\n" +
                "1,John,2.231"

        def rules = new ValidationRules()

        "Set up the rule - column with index 1 ('Name') should be longer than 5 characters"
        rules.with {
            column[1] = Format.length(min: 5)
        }

        when:
        "Validation is performed"
        CsvValidator validator = new CsvValidator(csv, rules)
        validator.validate()


        then:
        "ColumnLengthViolatesConstraint is thrown"
        thrown(ColumnLengthViolatesConstraint)
    }


}
