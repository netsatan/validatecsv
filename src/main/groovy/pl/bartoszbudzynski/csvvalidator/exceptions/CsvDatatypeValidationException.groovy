package pl.bartoszbudzynski.csvvalidator.exceptions

class CsvDatatypeValidationException extends CsvValidationException {

    CsvDatatypeValidationException(String message) {
        super(message)
    }

    CsvDatatypeValidationException() {
        super("Datatype mismatch!")
    }

}
