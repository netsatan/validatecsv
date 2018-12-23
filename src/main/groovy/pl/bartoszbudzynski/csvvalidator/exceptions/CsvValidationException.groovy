package pl.bartoszbudzynski.csvvalidator.exceptions

class CsvValidationException extends Exception{
    CsvValidationException(String message) {
        super(message)
    }

    CsvValidationException() {
        super()
    }
}
