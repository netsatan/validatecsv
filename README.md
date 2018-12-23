# validatecsv
## What is it? 
It's a small project created in Groovy allowing you to test if generated CSV is valid in terms of data types, cell lenght, format etc. 

It can be used to help you make sure that data exported from your system is correct and as a safeguard for data 
sanitization.

## Getting started
### Examples
Set up the validation pl.bartoszbudzynski.csvvalidator.rules first:


```
def rules = { 
    column[0] = DataType.INT
    column[1] = DataType.STRING
    column[3] = Format.of('\\d{3}')
    column[5] = [Format.length(max=3), DataType.FLOAT]
}
```

You can set up validation rules for all columns or only the ones you actually want to validate.
There might be a one rule or a whole list, as in example with column indexed with number 5.

### Running the rests
Tests are written using Spock. You can run them by simply typing
```./gradlew test```.

