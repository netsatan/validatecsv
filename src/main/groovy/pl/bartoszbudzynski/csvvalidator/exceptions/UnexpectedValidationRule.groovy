package pl.bartoszbudzynski.csvvalidator.exceptions

class UnexpectedValidationRule extends CsvValidationException {
    UnexpectedValidationRule() {
        super("Unexpected validation rule set - cannot perform validation")
    }
}
