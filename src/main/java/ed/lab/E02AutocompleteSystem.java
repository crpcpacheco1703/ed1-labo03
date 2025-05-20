package ed.lab;

import java.util.*;

public class E02AutocompleteSystem {

    private Map<String, Integer> frequencyMap = new HashMap<>();
    private StringBuilder currentInput = new StringBuilder();

    public E02AutocompleteSystem(String[] sentences, int[] times) {
        for (int i = 0; i < sentences.length; i++) {
            frequencyMap.put(sentences[i], frequencyMap.getOrDefault(sentences[i], 0) + times[i]);
        }
    }

    public List<String> input(char c) {
        if (c == '#') {
            String sentence = currentInput.toString();
            frequencyMap.put(sentence, frequencyMap.getOrDefault(sentence, 0) + 1);
            currentInput.setLength(0); // reset input
            return new ArrayList<>();
        }

        currentInput.append(c);
        String prefix = currentInput.toString();

        List<String> candidates = new ArrayList<>();
        for (String sentence : frequencyMap.keySet()) {
            if (sentence.startsWith(prefix)) {
                candidates.add(sentence);
            }
        }

        candidates.sort((a, b) -> {
            int freqA = frequencyMap.get(a);
            int freqB = frequencyMap.get(b);
            if (freqA != freqB) return freqB - freqA;
            return a.compareTo(b);
        });

        return candidates.subList(0, Math.min(3, candidates.size()));
    }
}
