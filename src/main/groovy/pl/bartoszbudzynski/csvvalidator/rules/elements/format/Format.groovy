package pl.bartoszbudzynski.csvvalidator.rules.elements.format

import pl.bartoszbudzynski.csvvalidator.rules.Rule

/**
 * Format is a simple factory that builds the FormatValidator that is needed at the moment.
 * It uses variety of default parameters to make usage as simple and intuitive as possible for the programmer
 * (hopefully not trading of too much on implementation complexity).
 */
class Format implements Rule{


    static FormatValidator of(String regex) {
        new FormatValidator(regex: regex)
    }

    static FormatValidator length(Map args) {
        new FormatValidator(maxLength: args.max, minLength: args.min, exactLength: args.exact)
    }

    static FormatValidator length(int exact) {
        new FormatValidator(exactLength: exact)
    }

}
