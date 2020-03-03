package programmers.bestAlbum;

import java.util.*;

/*
코딩테스트 연습 > 해시 > 베스트앨범
https://programmers.co.kr/learn/courses/30/lessons/42579

 */
public class Solution {
    public int[] solution(String[] genres, int[] plays) {
        Map<String, PriorityQueue<OnePair<Integer>>> playMap = new HashMap<>();
        Map<String, OnePair<String>> genreMap = new HashMap<>();
        List<Integer> list = new ArrayList<>();

        int len = genres.length;
        for(int i=0; i<len; i++) {
            String genre = genres[i];
            int play = plays[i];
            if(!genreMap.containsKey(genre)) genreMap.put(genre, new OnePair<>(genre, 0));
            if(!playMap.containsKey(genre)) playMap.put(genre, new PriorityQueue<>());
            genreMap.get(genre).addValue(play);
            playMap.get(genre).add(new OnePair<Integer>(i, play));
        }

        PriorityQueue<OnePair<String>> genrePQ = new PriorityQueue<>();
        for(OnePair<String> one : genreMap.values()) {
            genrePQ.add(one);
        }
        while(genrePQ.size() > 0) {
            OnePair<String> genrePair = genrePQ.poll();
            PriorityQueue<OnePair<Integer>> playPQ = playMap.get(genrePair.key);
            int loopLimit = Math.min(2, playPQ.size());
            for(int i=0; i<loopLimit; i++) {
                list.add(playPQ.poll().key);
            }
        }
        int[] ret = new int[list.size()];
        for(int i=0; i<list.size(); i++)
            ret[i] = list.get(i);
        return ret;
    }
}

class OnePair <T> implements Comparable<OnePair>{
    T key;
    int value;
    OnePair(T key, int value) {
        this.key = key;
        this.value = value;
    }
    public void addValue(int value) {
        this.value += value;
    }
    @Override
    public int compareTo(OnePair myPair) {
        return myPair.value - value;
    }
    @Override
    public String toString() {
        return String.format("[key:%s, value:%s]", key, value);
    }
}