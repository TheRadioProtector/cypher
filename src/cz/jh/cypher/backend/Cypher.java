package cz.jh.cypher.backend;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Cypher {
	private static final int ORIGINAL_ROW = 0;
	private static final int KEYWORD_ROW = 1;
	private static final int COMPLETE_ROW = 2;
	private static final List<Character> ALPHABET = initAlphabet();
	private static final Map<Integer, Character> ORIGINAL_CHAR_MAP = initOriginalCharMap();
	private final JTable table;
	private Map<Integer, Character> keyedMap;
	private Map<Integer, Character> completeMap;
	private String keyword;
	private int offset;

	public Cypher(JTable table) {
		this.table = table;
		keyword = "";
		offset = 0;
		updateAlphabet();
	}

	private static List<Character> initAlphabet() {
		List<Character> alphabet = Arrays.asList('a', '·', 'b', 'c', 'Ë', 'd', 'Ô', 'e', 'È', 'Ï', 'f', 'g', 'h', 'i',
				'Ì', 'j', 'k', 'l', 'm', 'n', 'Ú', 'o', 'Û', 'p', 'q', 'r', '¯', 's', 'ö', 't', 'ù', 'u', '˙', '˘', 'v',
				'w', 'x', 'y', '˝', 'z', 'û');
		return Collections.unmodifiableList(alphabet);
	}

	private static Map<Integer, Character> initOriginalCharMap() {
		Map<Integer, Character> charMap = IntStream.range(0, ALPHABET.size()).boxed()
				.collect(Collectors.toMap(Function.identity(), ALPHABET::get));
		return Collections.unmodifiableMap(charMap);
	}

	private void updateAlphabet() {
		updateRow(ORIGINAL_CHAR_MAP, ORIGINAL_ROW);
		updateKeymap(keyword);
	}

	private void updateRow(Map<Integer, Character> map, int row) {
		map.forEach((key, value) -> table.setValueAt(Character.toUpperCase(value), row, key));
	}

	public void updateKeymap(String keyword) {

		List<Character> mutableList = new ArrayList<>(ALPHABET);
		Map<Integer, Character> mutableMap = new HashMap<>();
		int mapIndex = 0;

		for (char character : keyword.toCharArray()) {
			char lowerCase = Character.toLowerCase(character);

			if (mutableList.contains(lowerCase)) {
				int listIndex = mutableList.indexOf(lowerCase);
				mutableList.remove(listIndex);
				mutableMap.put(mapIndex++, lowerCase);
			}
		}

		for (char character : mutableList) {
			mutableMap.put(mapIndex++, character);
		}

		keyedMap = Collections.unmodifiableMap(mutableMap);
		updateRow(keyedMap, KEYWORD_ROW);
		updateCompleteMap(offset);
	}

	public void updateCompleteMap(int offset) {
		this.offset = offset;
		Map<Integer, Character> map = keyedMap.entrySet().stream()
				.collect(Collectors.toMap(entry -> calculateNewIndex(entry.getKey(), offset), Entry::getValue));
		completeMap = Collections.unmodifiableMap(map);
		updateRow(completeMap, COMPLETE_ROW);
	}

	public int convert(File source, File target, ConvertMode mode) throws IOException {

		int counter = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(source));
				BufferedWriter writer = new BufferedWriter(new FileWriter(target))) {

			String line;
			while ((line = reader.readLine()) != null) {
				String convertedLine = convertLine(line, mode);
				counter++;
				writer.write(convertedLine);
				writer.newLine();
			}
		}

		return counter;
	}

	private String convertLine(String line, ConvertMode mode) {
		StringBuilder sb = new StringBuilder();
		Map<Integer, Character> from;
		Map<Integer, Character> to;

		if (ConvertMode.CYPHER == mode) {
			from = ORIGINAL_CHAR_MAP;
			to = completeMap;
		} else {
			from = completeMap;
			to = ORIGINAL_CHAR_MAP;
		}

		line.chars().boxed().map(charPoint -> convertCharacter(charPoint, from, to)).forEach(sb::append);
		return sb.toString();
	}

	private char convertCharacter(int charPoint, Map<Integer, Character> from, Map<Integer, Character> to) {

		char original = (char) charPoint;
		char lowerCase = Character.toLowerCase(original);

		if (isSupported(lowerCase)) {
			int pos = getPosOf(lowerCase, from);
			char encodedChar = to.get(pos);

			if (Character.isUpperCase(original)) {
				return Character.toUpperCase(encodedChar);
			} else {
				return encodedChar;
			}

		} else {
			return original;
		}
	}

	private static int getPosOf(char character, Map<Integer, Character> map) {
		return map.entrySet().stream().filter(entry -> entry.getValue().equals(character)).findFirst()
				.map(Entry::getKey).get();
	}

	private static boolean isSupported(char character) {
		return ALPHABET.contains(character);
	}

	private static int calculateNewIndex(int startPos, int offset) {

		if (offset > 0) {
			return (startPos + offset) % ALPHABET.size();
		} else if (offset < 0) {
			int sum = startPos + offset;
			return sum < 0 ? ALPHABET.size() - -sum % ALPHABET.size() : sum;
		} else {
			return startPos;
		}
	}
}
