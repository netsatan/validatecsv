# validatecsv
## What is it? 
It's a small project created in Groovy allowing you to test if generated CSV is valid in terms of data types, cell length, format etc. 

It can be used to help you make sure that data exported from your system is correct and as a safeguard for data 
sanitization.

## Getting started
### Installing
You can get this library from JitPack repository, depending on your build system:
#### Gradle
First, add JitPack to your `repositories`: 
```
repositories {
       maven { url 'https://jitpack.io' }
}
```

Then add this line to `dependencies`:
```
compile "com.github.netsatan:validatecsv:0.0.1"
``` 

### Examples
Set up the pl.bartoszbudzynski.csvvalidator.rules.ValidationRules first:
```
def rules = new ValidationRules
rules.with = { 
    column[0] = DataType.INT
    column[1] = DataType.STRING
    column[3] = Format.of('\\d{3}')
    column[5] = [Format.length(max: 3), DataType.FLOAT]
}
```

You can set up validation rules for all columns or only the ones you actually want to validate.
There might be a one rule or a whole list, as in example with column indexed with number 5.

After that, simply create an instance of CsvValidator: `CsvValidator validator = new CsvValidator(csv, rules)` and call `validator.validate()`.

### Running the tests
Tests are written using Spock. You can run them by simply typing
```./gradlew test```.

