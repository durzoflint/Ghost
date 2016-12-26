package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    public ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null)
        {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(word);
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix)
    {
        if(prefix.length()==0)
        {
            Random r=new Random();
            return words.get(r.nextInt(words.size()));
        }
        else
        return binarySearch(prefix);
    }

    public String binarySearch(String s)
    {
        int size=words.size(),first,last,mid=0;
        String midString="";
        first=0;
        last=size-1;
        while(first<=last)
        {
            mid=(first+last)/2;
            midString=words.get(mid).toString();
            if(midString.indexOf(s)!=-1) {
                return midString;
            }
            else if(midString.compareTo(s)<0)
            {
                first=mid+1;
            }
            else if(midString.compareTo(s)>0)
            {
                last=mid-1;
            }
        }
        return null;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        return null;
    }
}
