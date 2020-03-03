/**╔═══════════════════════════════════════════════╦═════════════════════════╦══════════╗
 * ║ NumberToString                                ║ Created:    5 Nov. 2017 ║ v1.0.0.1 ║
 * ║                                               ║ Last mod.:  6 May  2019 ╚══════════╣
 * ╠═══════════════════════════════════════════════╩════════════════════════════════════╣
 * ║ 	An efficient class that converts any integer into its English-text equivalent.  ║
 * ║ Can convert to cardinal number (e.g. 1,234 = one thousand two hundred thirty-four),║
 * ║ or to an ordinal number (e.g. 1,234 = one thousand two hundred thirty-fourth).     ║
 * ║ Contains options for negative numbers as well as adding/omitting commas, hyphens,  ║
 * ║ the word 'and', and ability to abbreviate ordinality (e.g. 1,234th).               ║
 * ║ 	Possibly coming someday: other languages, fractions/decimals, BigInteger        ║ 
 * ║ support.                                                                           ║
 * ╠════════════════════════════════════════════════════════════════════════════════════╣
 * ║ For the latest version of this snippet, report a bug, or to contribute, please     ║ 
 * ║ visit:     github.com/snealbli/field.js                                            ║
 * ║    or:     robot.nealblim.com/field.js                                             ║
 * ╠════════════════════════════════════════════════════════════════════════════════════╣
 * ║                             by Samuel 'teer' Neal-Blim                             ║
 * ║                                                                                    ║
 * ║                              Site: prog.nealblim.com                               ║
 * ║                              Git:  github.com/snealbli                             ║
 * ║                         JSfiddle:  jsfiddle.net/user/teeer                         ║
 * ╚════════════════════════════════════════════════════════════════════════════════════╝
 */
package com.sknb.util;

import java.util.Arrays;


/**
 * @class NumberToString
 * @author Sam Neal-Blim <sam@nealblim.com>
 */
public class NumberToString {
	/**
	 * 
	 */
    public static final LargeNumber[] NUMBERS = LargeNumber.values();
    
    
    /**
     * 
     * @param number
     * @return 
     */
    public static String numberToString(long number) {  
        return numberToString(number, true, true, true);
    }
    
    /**
     * 
     * @param number
     * @param includeHyphens
     * @param includeCommas
     * @param includeAnd
     * @return
     */
    public static String numberToString(long number,
			   							boolean includeHyphens, 
			   							boolean includeCommas, 
			   							boolean includeAnd) {  
        if (number == 0) {
            return "zero";
        } else {
            StringBuilder sb = new StringBuilder();

            if (number < 0) {
                sb.append("negative ");
                number *= -1;
            }
            
            for (int num = Arrays.binarySearch(NUMBERS, LargeNumber.approximate(number)); (number > 0); number %= NUMBERS[num].value, num--) {
                int triplet = (int)(number / NUMBERS[num].value);
                
                if (triplet > 0) {
                    if (sb.length() > 0) {
                        if ((includeAnd) && (number < 100)) {
                            sb.append(" and ");
                        } else if (includeCommas) {
                            sb.append(", ");
                        }
                    }
                    
                    sb.append(parseTriplet(triplet, includeAnd));
                    
                    if (num > 0) {
                        sb.append(" ").append(NUMBERS[num].name);
                    }
                }
            }
            
            return sb.toString();
        }
    }
    
    /**
     * 
     * @param number
     * @return 
     */
    public static String numberToOrdinalString(long number) {
        return numberToOrdinalString(number, false, true, true, false);
    }
    
	/**
	 * 
	 * @param number
	 * @param abbreviate
	 * @return
	 */
    public static String numberToOrdinalString(long number,
    										   boolean abbreviate) {
        return numberToOrdinalString(number, true, true, true, false);
    }

    /**
     * 
     * 
     * @param number
     * @param includeHyphens
     * @param includeCommas
     * @param includeAnd
     * @return
     */
    public static String numberToOrdinalString(long number, 
    										   boolean abbreviate,
    										   boolean includeHyphens, 
    										   boolean includeCommas, 
    										   boolean includeAnd) {
        int lastTwoDigits = (int) number % 100;        //Get last 2 digits
        StringBuilder sb = new StringBuilder(numberToString(number));
        String suffix = null;
        
        if ((lastTwoDigits / 10) != 1) {
	        switch (lastTwoDigits % 10) {
	        	case 0:		if (lastTwoDigits > 10) {
	        					sb.deleteCharAt(sb.length() - 1);
	        					sb.append("ieth");
	        				} else {
	        					sb.append("th");
	        				}

	        				break;
	            case 1:     suffix = "first";
	                        break;
	            case 2:     suffix = "second";
	                        break;
	            case 3:     suffix = "third";
	                        break;
	            case 5:     suffix = "fifth";
	                        break;
	            case 8:     suffix = "eighth";
	                        break;
	            case 9:     suffix = "ninth";
	                        break;
	            default:    sb.append("th");
	        }
        } else {
        	switch (lastTwoDigits % 10) {
        	 	case 1:     suffix = (lastTwoDigits == 11) ? "eleventh" : "first";
        	 				break;
        	 	case 2:     suffix = (lastTwoDigits == 12) ? "twelfth" : "second";
        	 				break;
        	 	default:    sb.append("th");
        	}
        }
        
        if (suffix != null) {
            int i = sb.lastIndexOf("-");
            i = (i < 0) ? sb.lastIndexOf(" ") + 1 : ++i;
            
            sb.replace(i, i + suffix.length(), suffix);
            
            if ((includeHyphens) && (number > 100)) {
            	sb.replace(i - 1, i, "-");
            }
        }
        
        return sb.toString();
    }
    
	/**
	 * 
	 * @param digits
	 * @param includeAnd
	 * @return
	 */
    private static String parseTriplet(int digits, boolean includeAnd) {
        if (digits == 0) {
            return null;
        } else {
            StringBuilder str = new StringBuilder();
            
     inner: for (int currentDigit = (digits >= 100) ? digits / 100 : digits % 100; digits > 0; currentDigit = digits) {        
                if (currentDigit >= 10) {
                    currentDigit /= 10;

                    switch (currentDigit) {
                        case 1:
                            switch (digits % 10) {
                                case 0:
                                    str.append("ten");
                                    break;
                                case 1:
                                    str.append("eleven");
                                    break;
                                case 2:
                                    str.append("twelve");
                                    break;
                                case 3:
                                    str.append("thirteen");
                                    break;
                                case 4:
                                    str.append("fourteen");
                                    break;
                                case 5:
                                    str.append("fifteen");
                                    break;
                                case 6:
                                    str.append("sixteen");
                                    break;
                                case 7:
                                    str.append("seventeen");
                                    break;
                                case 8:
                                    str.append("eighteen");
                                    break;
                                case 9:
                                    str.append("nineteen");
                                    break;
                            }

                        break inner;
                    case 2:
                        str.append("twenty");
                        break;
                    case 3:
                        str.append("thirty");
                        break;
                    case 4:
                        str.append("forty");
                        break;    
                    case 5:
                        str.append("fifty");
                        break;
                    case 6:
                        str.append("sixty");
                        break;
                    case 7:
                        str.append("seventy");
                        break;
                    case 8:
                        str.append("eighty");
                        break;    
                    case 9:
                        str.append("ninety");
                        break;
                    }

                    currentDigit = digits % 10;

                    if (currentDigit > 0) {
                        str.append("-");
                    } else {
                        break;
                    }
                }

                switch (currentDigit) {
                    case 1:
                       str.append("one");
                       break;
                    case 2:
                       str.append("two");
                       break;
                    case 3:
                       str.append("three");
                       break;
                    case 4:
                       str.append("four");
                       break;
                    case 5:
                       str.append("five");
                       break;
                    case 6:
                       str.append("six");
                       break;
                    case 7:
                       str.append("seven");
                       break;
                    case 8:
                       str.append("eight");
                       break;
                    case 9:
                        str.append("nine");
                        break;
                }

                if (digits >= 100) {
                    str.append(" hundred");
                    
                    if (((digits % 100) != 0) && (includeAnd)) {
                        str.append(" and");
                    }
                    
                    digits %= 100;
                    
                    if (digits != 0) {
                        str.append(" ");
                    }
                } else {
                    
                    
                    break;
                }
            }
     
            return str.toString();
        }
    }
    
	/**
	 * 
	 * @author Sam
	 *
	 */
    public enum LargeNumber {
        ONE         (1L,                    ""), 
        THOUSAND    (1000L,                 "thousand"),
        MILLION     (1000000L,              "million"),
        BILLION     (1000000000L,           "billion"),
        TRILLION    (1000000000000L,        "trillion"),
        QUADRILLION (1000000000000000L,     "quadrillion"),
        QUINTILLION (1000000000000000000L,  "quintillion");

        private final long value;
        private final String name;
        LargeNumber(long value, String name) {
            this.value = value; 
            this.name = name;
        }

        /**
         * 
         * @return 
         */
        public long getValue() {
            return this.value;
        }

        /**
         * 
         * @return 
         */
        public String getName() {
            return this.name;
        }

        /**
         * 
         * @param number
         * @return 
         */
        public static LargeNumber approximate(long number) {
            if (number >= LargeNumber.QUINTILLION.value) {
                return QUINTILLION;
            } else if (number >= LargeNumber.QUADRILLION.value) {
                return QUADRILLION;
            } else if (number >= LargeNumber.TRILLION.value) {
                return TRILLION;
            } else if (number >= LargeNumber.BILLION.value) {
                return BILLION;
            } else if (number >= LargeNumber.MILLION.value) {
                return MILLION;
            } else if (number >= LargeNumber.THOUSAND.value) {
                return THOUSAND;
            } else {
                return ONE;
            }
        }
    }
}