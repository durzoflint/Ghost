package com.google.engedu.ghost;

import java.util.HashMap;
import java.util.Random;

public class TrieNode
{
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode()
    {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s)
    {
        TrieNode tn=this,child;

        for(int i=0;i<s.length();i++)
        {
            String st=s.charAt(i)+"";
            if(tn.children.containsKey(st))
            {
                tn= tn.children.get(st);
            }
            else
            {
                child=new TrieNode();
                tn.children.put(st,child);
                tn=child;
            }
        }
        tn.isWord=true;
    }

    public boolean isWord(String s)
    {
        TrieNode tn=this;
        for (int i=0;i<s.length();i++)
        {
            String st=s.charAt(i)+"";
            if(tn.children.containsKey(st+""))
            {
                tn=tn.children.get(st);
            }
        }
        if(tn.isWord==true)
            return true;
        else
            return false;
    }

    public String getAnyWordStartingWith(String s)
    {
        TrieNode tn=this;
        int c=0;
        for (int i=0;i<s.length();i++)
        {
            String st=s.charAt(i)+"";
            if(tn.children.containsKey(st))
            {
                tn=tn.children.get(st);
                c++;
            }
        }
        if(s.length()==c)
        for(char ch='a';ch<='z';ch++)
            if(tn.children.containsKey(ch+"")){
                return s+ch;
            }
        return null;
    }

    public String getGoodWordStartingWith(String s)
    {
        TrieNode tn=this;
        int c=0;
        for (int i=0;i<s.length();i++)
        {
            String st=s.charAt(i)+"";
            if(tn.children.containsKey(st))
            {
                c++;
                tn=tn.children.get(st);
            }
        }
        if(c==s.length())
        {
            int cc=0;
            char a[]=new char[26];
            for(char ch='a';ch<='z';ch++)
                if(tn.children.containsKey(ch+""))
                a[cc++]=ch;
            Random r = new Random();
            int n = r.nextInt(cc);
            return s + a[n];
        }
        return null;
    }
}
