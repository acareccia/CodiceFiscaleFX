package com.ilfalsodemetrio.utils;

import com.ilfalsodemetrio.utils.entity.Persona;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CodiceFiscaleChecker {
	
	protected static final HashMap<Character, Integer> literalRegionalCodeMapper = new HashMap<Character, Integer>();
	protected static final HashMap<Character, Integer> corrispondenzeDispariCodiceFiscale = new HashMap<Character, Integer>();
	protected static final HashMap<Character, Integer> corrispondenzePariCodiceFiscale = new HashMap<Character, Integer>();
	protected static final HashMap<Character, Character> omocodeCorrespondences = new HashMap<Character, Character>();
	protected static final HashMap<Integer, Character> checksumCorrespondences = new HashMap<Integer, Character>();
	protected static final HashMap<String, Character> monthCorrespondencesCodiceFiscale = new HashMap<String, Character>();
	
	private static final String CD_FISCALE_REGEX = "[A-Z a-z 0-9]{16}";
	
	private static final char[] DECODE_MESE = {'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T'};
    private static final char[] DECODE_OMON = {'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V' };
    private static String[] MATRIX = new String[127];
    static{
		
		omocodeCorrespondences.put('L', '0');
		omocodeCorrespondences.put('M', '1');
		omocodeCorrespondences.put('N', '2');
		omocodeCorrespondences.put('P', '3');
		omocodeCorrespondences.put('Q', '4');
		omocodeCorrespondences.put('R', '5');
		omocodeCorrespondences.put('S', '6');
		omocodeCorrespondences.put('T', '7');
		omocodeCorrespondences.put('U', '8');
		omocodeCorrespondences.put('V', '9');
		
		literalRegionalCodeMapper.put('A', 0);
		literalRegionalCodeMapper.put('B', 1);
		literalRegionalCodeMapper.put('C', 2);
		literalRegionalCodeMapper.put('F', 3);
		literalRegionalCodeMapper.put('H', 4);
		literalRegionalCodeMapper.put('J', 5);
		literalRegionalCodeMapper.put('K', 6);
		literalRegionalCodeMapper.put('L', 7);
		literalRegionalCodeMapper.put('M', 8);
		literalRegionalCodeMapper.put('P', 9);
		literalRegionalCodeMapper.put('S', 10);
		literalRegionalCodeMapper.put('T', 11);
		literalRegionalCodeMapper.put('V', 12);
		literalRegionalCodeMapper.put('W', 13);
		literalRegionalCodeMapper.put('X', 14);
		literalRegionalCodeMapper.put('Y', 15);
		
		corrispondenzeDispariCodiceFiscale.put('0', 1);
		corrispondenzeDispariCodiceFiscale.put('1', 0);
		corrispondenzeDispariCodiceFiscale.put('2', 5);
		corrispondenzeDispariCodiceFiscale.put('3', 7);
		corrispondenzeDispariCodiceFiscale.put('4', 9);
		corrispondenzeDispariCodiceFiscale.put('5', 13);
		corrispondenzeDispariCodiceFiscale.put('6', 15);
		corrispondenzeDispariCodiceFiscale.put('7', 17);
		corrispondenzeDispariCodiceFiscale.put('8', 19);
		corrispondenzeDispariCodiceFiscale.put('9', 21);
		corrispondenzeDispariCodiceFiscale.put('A', 1);
		corrispondenzeDispariCodiceFiscale.put('B', 0);
		corrispondenzeDispariCodiceFiscale.put('C', 5);
		corrispondenzeDispariCodiceFiscale.put('D', 7);
		corrispondenzeDispariCodiceFiscale.put('E', 9);
		corrispondenzeDispariCodiceFiscale.put('F', 13);
		corrispondenzeDispariCodiceFiscale.put('G', 15);
		corrispondenzeDispariCodiceFiscale.put('H', 17);
		corrispondenzeDispariCodiceFiscale.put('I', 19);
		corrispondenzeDispariCodiceFiscale.put('J', 21);
		corrispondenzeDispariCodiceFiscale.put('K', 2);
		corrispondenzeDispariCodiceFiscale.put('L', 4);
		corrispondenzeDispariCodiceFiscale.put('M', 18);
		corrispondenzeDispariCodiceFiscale.put('N', 20);
		corrispondenzeDispariCodiceFiscale.put('O', 11);
		corrispondenzeDispariCodiceFiscale.put('P', 3);
		corrispondenzeDispariCodiceFiscale.put('Q', 6);
		corrispondenzeDispariCodiceFiscale.put('R', 8);
		corrispondenzeDispariCodiceFiscale.put('S', 12);
		corrispondenzeDispariCodiceFiscale.put('T', 14);
		corrispondenzeDispariCodiceFiscale.put('U', 16);
		corrispondenzeDispariCodiceFiscale.put('V', 10);
		corrispondenzeDispariCodiceFiscale.put('W', 22);
		corrispondenzeDispariCodiceFiscale.put('X', 25);
		corrispondenzeDispariCodiceFiscale.put('Y', 24);
		corrispondenzeDispariCodiceFiscale.put('Z', 23);
		
		corrispondenzePariCodiceFiscale.put('0', 0);
		corrispondenzePariCodiceFiscale.put('1', 1);
		corrispondenzePariCodiceFiscale.put('2', 2);
		corrispondenzePariCodiceFiscale.put('3', 3);
		corrispondenzePariCodiceFiscale.put('4', 4);
		corrispondenzePariCodiceFiscale.put('5', 5);
		corrispondenzePariCodiceFiscale.put('6', 6);
		corrispondenzePariCodiceFiscale.put('7', 7);
		corrispondenzePariCodiceFiscale.put('8', 8);
		corrispondenzePariCodiceFiscale.put('9', 9);
		corrispondenzePariCodiceFiscale.put('A', 0);
		corrispondenzePariCodiceFiscale.put('B', 1);
		corrispondenzePariCodiceFiscale.put('C', 2);
		corrispondenzePariCodiceFiscale.put('D', 3);
		corrispondenzePariCodiceFiscale.put('E', 4);
		corrispondenzePariCodiceFiscale.put('F', 5);
		corrispondenzePariCodiceFiscale.put('G', 6);
		corrispondenzePariCodiceFiscale.put('H', 7);
		corrispondenzePariCodiceFiscale.put('I', 8);
		corrispondenzePariCodiceFiscale.put('J', 9);
		corrispondenzePariCodiceFiscale.put('K', 10);
		corrispondenzePariCodiceFiscale.put('L', 11);
		corrispondenzePariCodiceFiscale.put('M', 12);
		corrispondenzePariCodiceFiscale.put('N', 13);
		corrispondenzePariCodiceFiscale.put('O', 14);
		corrispondenzePariCodiceFiscale.put('P', 15);
		corrispondenzePariCodiceFiscale.put('Q', 16);
		corrispondenzePariCodiceFiscale.put('R', 17);
		corrispondenzePariCodiceFiscale.put('S', 18);
		corrispondenzePariCodiceFiscale.put('T', 19);
		corrispondenzePariCodiceFiscale.put('U', 20);
		corrispondenzePariCodiceFiscale.put('V', 21);
		corrispondenzePariCodiceFiscale.put('W', 22);
		corrispondenzePariCodiceFiscale.put('X', 23);
		corrispondenzePariCodiceFiscale.put('Y', 24);
		corrispondenzePariCodiceFiscale.put('Z', 25);
		
		checksumCorrespondences.put(0, 'A');
		checksumCorrespondences.put(1, 'B');
		checksumCorrespondences.put(2, 'C');
		checksumCorrespondences.put(3, 'D');
		checksumCorrespondences.put(4, 'E');
		checksumCorrespondences.put(5, 'F');
		checksumCorrespondences.put(6, 'G');
		checksumCorrespondences.put(7, 'H');
		checksumCorrespondences.put(8, 'I');
		checksumCorrespondences.put(9, 'J');
		checksumCorrespondences.put(10, 'K');
		checksumCorrespondences.put(11, 'L');
		checksumCorrespondences.put(12, 'M');
		checksumCorrespondences.put(13, 'N');
		checksumCorrespondences.put(14, 'O');
		checksumCorrespondences.put(15, 'P');
		checksumCorrespondences.put(16, 'Q');
		checksumCorrespondences.put(17, 'R');
		checksumCorrespondences.put(18, 'S');
		checksumCorrespondences.put(19, 'T');
		checksumCorrespondences.put(20, 'U');
		checksumCorrespondences.put(21, 'V');
		checksumCorrespondences.put(22, 'W');
		checksumCorrespondences.put(23, 'X');
		checksumCorrespondences.put(24, 'Y');
		checksumCorrespondences.put(25, 'Z');
		
		monthCorrespondencesCodiceFiscale.put("01", 'A');
		monthCorrespondencesCodiceFiscale.put("02", 'B');
		monthCorrespondencesCodiceFiscale.put("03", 'C');
		monthCorrespondencesCodiceFiscale.put("04", 'D');
		monthCorrespondencesCodiceFiscale.put("05", 'E');
		monthCorrespondencesCodiceFiscale.put("06", 'H');
		monthCorrespondencesCodiceFiscale.put("07", 'L');
		monthCorrespondencesCodiceFiscale.put("08", 'M');
		monthCorrespondencesCodiceFiscale.put("09", 'P');
		monthCorrespondencesCodiceFiscale.put("10", 'R');
		monthCorrespondencesCodiceFiscale.put("11", 'S');
		monthCorrespondencesCodiceFiscale.put("12", 'T');
    	
        for (int i = 0; i < MATRIX.length; i++) {
            String stringaBinaria = Integer.toBinaryString(i + 1);
            while (stringaBinaria.length() < 7) {
                stringaBinaria = "0" + stringaBinaria;
            }
            String st1 = stringaBinaria.substring(0, 2);
            String st2 = stringaBinaria.substring(2, 4);
            String st3 = stringaBinaria.substring(4, 7);
            MATRIX[i] = "000000" + st1 + "0" + st2 + "0" + st3;
        }
    }

	public static void check(Persona persona) {
		if(persona == null)
			throw new CodiceFiscaleException();

		if(persona.getCodiceFiscale() == null || persona.getCodiceFiscale().length() != 16)
			throw new CodiceFiscaleException();
		
		boolean check = false;
		List<String> validCodiciFiscali = getAllValidCodiciFiscali(persona);
		for (String codiceFiscaleOmocodo : validCodiciFiscali) {
			if(persona.getCodiceFiscale().equals(codiceFiscaleOmocodo)){
				check = true;
				break;
			}
		}
		
		if(!check){
			CodiceFiscaleException cfe = new CodiceFiscaleException();
			//cfe.setData(getAllValidCodiciFiscali(persona));
			throw cfe;
		}
	}

	public static boolean checkCdFiscaleFormat (String codiceFiscale) {
		if (!codiceFiscale.matches(CD_FISCALE_REGEX))
			return false;
		char[] chars = codiceFiscale.toCharArray();
		int[] indexes = new int[]{6, 7, 9, 10, 12, 13, 14};
		for (int i = 0; i < indexes.length; i++){
			if (CodiceFiscaleChecker.omocodeCorrespondences.containsKey(chars[indexes[i]]))
				chars[indexes[i]] = CodiceFiscaleChecker.omocodeCorrespondences.get(chars[indexes[i]]);
		}
		Pattern codiceFiscalePattern = Pattern.compile("(\\p{Upper}{6})(\\d{2})(\\p{Upper})(\\d{2})(\\p{Upper})(\\d{3})(\\p{Upper})");
		Matcher matcher = codiceFiscalePattern.matcher(new String(chars));
		if (!matcher.matches())
			return false;
		else
			return true;
	}

	public static List<String> getAllValidCodiciFiscali(Persona persona) {
		if(persona == null)
			throw new CodiceFiscaleException();
		
		checkRequiredAttributes(persona);
		String expectedCodiceFiscale = getExpectedCodiceFiscale(persona);
		List<String> validCodiciFiscali = new ArrayList<String>();
		validCodiciFiscali.add(expectedCodiceFiscale);
        
        for (int i = 0; i < MATRIX.length; i++) {
            String code = MATRIX[i];
            StringBuffer partialCodiceFiscaleOmocodo = new StringBuffer();
            for (int i2 = 0; i2 < code.length(); i2++) {
                char ch = code.charAt(i2);
                char expectedCodiceFiscaleCh = expectedCodiceFiscale.charAt(i2);
                int index = (expectedCodiceFiscaleCh) - 48;
                if (ch == '1') {
                	partialCodiceFiscaleOmocodo.append(DECODE_OMON[index]);
                } else {
                	partialCodiceFiscaleOmocodo.append(expectedCodiceFiscaleCh);
                }
            }
            char carattereControlloOmocodo = getCarattereControllo(partialCodiceFiscaleOmocodo.toString());
            String codiceFiscaleOmocodo = partialCodiceFiscaleOmocodo.toString() + carattereControlloOmocodo;
            validCodiciFiscali.add(codiceFiscaleOmocodo);
        }
		return validCodiciFiscali;		
	}

    private static String getPrimaParteCodiceFiscale(String cognome) {
        String primaParteCF = null;
        String consonantiCognome = getConsonant(cognome);
        if (consonantiCognome.length() > 3) {
            primaParteCF = consonantiCognome.substring(0, 3);
        } else if (consonantiCognome.length() == 3) {
            primaParteCF = consonantiCognome;
        } else {
            String cognomeVocal = getVocal(cognome);
            primaParteCF = consonantiCognome + cognomeVocal;
            if (primaParteCF.length() > 3) {
                primaParteCF = primaParteCF.substring(0, 3);
            } else if (primaParteCF.length() < 3) {
                for (int i = primaParteCF.length(); i < 3; i++) {
                    primaParteCF += 'X';
                }
            }
        }
        return primaParteCF;
    }

    private static String getSecondaParteCodiceFiscale(String nome) {
        String consonantiNome = getConsonant(nome);
        String secondaParteCF;
        if (consonantiNome.length() > 3) {
            secondaParteCF = consonantiNome.charAt(0) + consonantiNome.substring(2, 4);
        } else if (consonantiNome.length() == 3) {
            secondaParteCF = consonantiNome;
        } else {
            String nomeVocal = getVocal(nome);
            secondaParteCF = consonantiNome + nomeVocal;
            if (secondaParteCF.length() > 3) {
                secondaParteCF = secondaParteCF.substring(0, 3);
            } else if (secondaParteCF.length() < 3) {
                for (int i = secondaParteCF.length(); i < 3; i++) {
                    secondaParteCF += 'X';
                }
            }
        }
        return secondaParteCF;
    }

    private static String getTerzaParteCodiceFiscale(Date dataNascita, Character sesso) {
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataNascita);   	
    	int giorno = calendar.get(Calendar.DAY_OF_MONTH);
        int mese = calendar.get(Calendar.MONTH);
        int anno = calendar.get(Calendar.YEAR);
        char meseDecod = DECODE_MESE[mese];
        if(sesso.equals('F') || sesso.equals('f'))
            giorno += 40;
        StringBuffer terzaParte = new StringBuffer();
        terzaParte.append(Integer.toString(anno).substring(2)).
                append(meseDecod);
        if(giorno < 10)
            terzaParte.append('0');
        terzaParte.append(giorno);
        
    	return terzaParte.toString();
    }

	private static String getExpectedCodiceFiscale(Persona persona) {
		String cognome = persona.getCognome().trim().toUpperCase();
        String nome = persona.getNome().trim().toUpperCase();
        Date dataNascita = persona.getDataNascita();
        Character sesso = persona.getSesso();       
        String cdFiscale = persona.getLocalita().getCodiceFiscale().trim().toUpperCase();
        
        String firstPartCF = getPrimaParteCodiceFiscale(cognome);
        String secondPartCF = getSecondaParteCodiceFiscale(nome);
        String thirdPartCF = getTerzaParteCodiceFiscale(dataNascita, sesso);
                
	    String partialCodiceFiscale = firstPartCF + secondPartCF + thirdPartCF + cdFiscale;
        char carattereControllo = getCarattereControllo(partialCodiceFiscale);
        String expectedCodiceFiscale = partialCodiceFiscale + carattereControllo;
	    
        return expectedCodiceFiscale;
	}

    private static char getCarattereControllo(String partialCodiceFiscale) {
        int unevenSum = 0;
        for (int i = 0; i < partialCodiceFiscale.length(); i = i + 2) {
            int uneven = 0;
            char ch = partialCodiceFiscale.charAt(i);
            if ((ch == 'A') || (ch == '0')) {
                uneven = 1;
            } else if ((ch == 'B') || (ch == '1')) {
                uneven = 0;
            } else if ((ch == 'C') || (ch == '2')) {
                uneven = 5;
            } else if ((ch == 'D') || (ch == '3')) {
                uneven = 7;
            } else if ((ch == 'E') || (ch == '4')) {
                uneven = 9;
            } else if ((ch == 'F') || (ch == '5')) {
                uneven = 13;
            } else if ((ch == 'G') || (ch == '6')) {
                uneven = 15;
            } else if ((ch == 'H') || (ch == '7')) {
                uneven = 17;
            } else if ((ch == 'I') || (ch == '8')) {
                uneven = 19;
            } else if ((ch == 'J') || (ch == '9')) {
                uneven = 21;
            } else if (ch == 'K') {
                uneven = 2;
            } else if (ch == 'L') {
                uneven = 4;
            } else if (ch == 'M') {
                uneven = 18;
            } else if (ch == 'N') {
                uneven = 20;
            } else if (ch == 'O') {
                uneven = 11;
            } else if (ch == 'P') {
                uneven = 3;
            } else if (ch == 'Q') {
                uneven = 6;
            } else if (ch == 'R') {
                uneven = 8;
            } else if (ch == 'S') {
                uneven = 12;
            } else if (ch == 'T') {
                uneven = 14;
            } else if (ch == 'U') {
                uneven = 16;
            } else if (ch == 'V') {
                uneven = 10;
            } else if (ch == 'W') {
                uneven = 22;
            } else if (ch == 'X') {
                uneven = 25;
            } else if (ch == 'Y') {
                uneven = 24;
            } else if (ch == 'Z') {
                uneven = 23;
            }
            unevenSum += uneven;
        }

        int evenSum = 0;
        for (int i = 1; i < partialCodiceFiscale.length(); i = i + 2) {
            int even = 0;
            char ch = partialCodiceFiscale.charAt(i);
            try {
                even = Integer.parseInt("" + ch);
            } catch (NumberFormatException ex) {
                even = ((int)ch) - ((int)'A');
            }
            evenSum += even;
        }

        int sum = unevenSum + evenSum;
        int alphabeticalPosition = sum % 26;
        char carattereControllo = (char)(((int)'A') + alphabeticalPosition);

        return carattereControllo;
    }

    private static String getVocal(String str) {
        String vocal = null;
        if (str != null) {
            vocal = "";
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (isVocal(ch)) {
                    vocal += ch;
                }
            }
        }
        return vocal;
    }

    private static String getConsonant(String str) {
        String consonant = "";
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (isConsonant(ch)) {
                    consonant += ch;
                }
            }
        }
        return consonant;
    }

    private static boolean isAlphabeticalLetter(char ch) {
        boolean isAlphabeticalLetter = false;
        if ((('a' <= ch) && (ch <= 'z')) || (('A' <= ch) && (ch <= 'Z'))) {
            isAlphabeticalLetter = true;
        }
        return isAlphabeticalLetter;
    }

    private static boolean isVocal(char ch) {
        boolean isVocal = false;
        if ((ch == 'a') || (ch == 'A') ||
            (ch == 'e') || (ch == 'E') ||
            (ch == 'i') || (ch == 'I') ||
            (ch == 'o') || (ch == 'O') ||
            (ch == 'u') || (ch == 'U')) {
            isVocal = true;
        }
        return isVocal;
    }

    private static boolean isConsonant(char ch) {
        boolean isConsonant = false;
        if (isAlphabeticalLetter(ch) && (!isVocal(ch))) {
            isConsonant = true;
        }
        return isConsonant;
    }
    
	private static void checkRequiredAttributes(Persona persona) {
		if(persona.getNome() == null)
			throw new CodiceFiscaleException();
		
		if(persona.getCognome() == null)
			throw new CodiceFiscaleException();
		
		if(persona.getSesso() == null)
			throw new CodiceFiscaleException();
		
		if(persona.getDataNascita() == null)
			throw new CodiceFiscaleException();
		
		if(persona.getLocalita() == null)
			throw new CodiceFiscaleException();

	}
}
