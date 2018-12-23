package pl.bartoszbudzynski.csvvalidator


import pl.bartoszbudzynski.csvvalidator.exceptions.CsvDatatypeValidationException
import pl.bartoszbudzynski.csvvalidator.exceptions.CsvValidationException
import pl.bartoszbudzynski.csvvalidator.rules.ValidationRules
import pl.bartoszbudzynski.csvvalidator.rules.elements.datatype.constants.DataType
import pl.bartoszbudzynski.csvvalidator.rules.elements.format.Format
import spock.lang.Specification

class MultipleRulesSpec extends Specification {

    def "Can deal with one validation rule provided as a list and throw exception when validation fails"() {
        given:
        String csv = "ID,Name,Ratio\n" +
                "1,John,2.231"

        def rules = new ValidationRules()

        rules.with {
            column[0] = DataType.INT

            "Singular rule can be also provided in collection and should be validated"
            column[1] = [DataType.INT]
        }

        when:
        CsvValidator validator = new CsvValidator(csv, rules)
        validator.validate()


        then:
        "Expect exception as column[1] doesn't match the INT rule"
        thrown(CsvDatatypeValidationException)
    }

    def "Can deal with one validation rule provided as a list and pass validation when conditions are met"() {
        given:
        String csv = "ID,Name,Ratio\n" +
                "1,John,2.231"

        def rules = new ValidationRules()

        rules.with {
            column[0] = DataType.INT
            column[1] = [DataType.STRING]

        }

        when:
        CsvValidator validator = new CsvValidator(csv, rules)
        validator.validate()


        then:
        "No exception should be thrown as column[1] matches STRING rule"
        notThrown(CsvDatatypeValidationException)
    }

    def "Two different type of rules can be applied at once"() {
        given:
        String csv = "ID,Name,Ratio\n" +
                "1,John,2.231"

        def rules = new ValidationRules()

        rules.with {
            column[1] = [DataType.STRING, Format.length(max: 5, min: 3), Format.of("\\w{4}")]

        }

        when:
        CsvValidator validator = new CsvValidator(csv, rules)
        validator.validate()


        then:
        "No exception should be thrown as column[1] matches STRING rule"
        notThrown(CsvValidationException)
    }

}
