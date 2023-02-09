package com.sg.assessments.basic.assessments.basic;

import java.util.*;

public class DogGenetics {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String name;
        int max = 100;
        int min = 1;
        int percentage;
        int percentageTotal = 0;
        String[] dogBreeds = {"King Doberman", "Chihuahua", "St. Bernard", "German Shepherd", "Labrador Retriever", "Golden Retriever", "Border Collie", "Afghan Hound", "Poodle", "Siberian Husky"};
        int dogBreedRandom = (int)(Math.random() * (dogBreeds.length));;

        System.out.print("What is your dog's name? ");
        name = input.nextLine();

        System.out.println("Well then, I have this highly reliable report on " + name + "'s prestigious background right here.\n");
        System.out.println(name + " is: \n");

        do {
            percentage = (int)(Math.random() * (max - min) + min);
            max -= percentage;
            percentageTotal += percentage;


            while(dogBreeds[dogBreedRandom] == null) {
                dogBreedRandom = (int)(Math.random() * (dogBreeds.length));
            }
            System.out.println(percentage + "% " + dogBreeds[dogBreedRandom]);
            dogBreeds[dogBreedRandom] = null;



        } while (percentage > 0 && percentageTotal < 100);

        System.out.println("\nWow, that's QUITE the dog!");
    }
}
