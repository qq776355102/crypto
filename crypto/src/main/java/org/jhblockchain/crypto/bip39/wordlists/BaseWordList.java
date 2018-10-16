package org.jhblockchain.crypto.bip39.wordlists;

import org.jhblockchain.crypto.bip39.WordList;

public abstract class BaseWordList implements WordList {

	abstract protected String[] getWords();
	
	public String getWord(final int index) {
		return getWords()[index];
	}

	public int getIndex(final String word) {
		String[] words = getWords();
		int size = words.length;
		if(word == null) {
			for(int i = 0; i < size; i++)
				if(words[i] == null)
					return i;
		} else {
			for(int i = 0; i < size; i++)
				if(word.equals(words[i]))
					return i;
		}
		return -1;
	}

    public char getSpace() {
        return ' ';
    }
}
