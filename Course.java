import java.util.Scanner;
import java.io.*;

public class Course{

    private static Student[] roster;
    private String name = null;
    private double grade = 0;
    private Student b = new Student(name, grade);
    static Scanner s = new Scanner(System.in);

    public static void main (String[] args){
        roster = loadFile("students.txt");

        Course c = new Course(roster);

        System.out.print("Select 1 to Search by Name, Select 2 to Search by Grade: ");
        String sSelect = s.nextLine();
        int select = Integer.parseInt(sSelect);
        if(select == 1){
            System.out.print("Please enter students name: ");
            String key = s.nextLine();
            int i = c.findStudentName(key);
            if(i > 0){
                System.out.println("Found Student at Index: " + i + 1);
            }
            else{
                System.out.println("Student Index not Found");
            }
        }
        else if(select == 2){
            System.out.print("Please enter students grade: ");
            String keyS = s.nextLine();
            double key = Double.parseDouble(keyS);
            double i = c.findStudentGrade(key,1,15);
            if(i > 0){
                System.out.println("Found Student at Index: " + (i+1));
            }
            else{
                System.out.println("Student Index not Found");
            }
        }
        else {
            System.out.println("Please enter a value between 1-2");
        }

    }
    Course (Student[] r){
        Course.roster = r;
    }

    public static Student getStudent(int index){
        return roster[index];
    }

    //linear search by name
    public int findStudentName (String key){
        int n = roster.length;
        for (int i = 0; i < n; i++) {
            if (roster[i].getName().equals(key)){

                return i;
            }
        }
        return -1;
    }

    //binary search by grade
    public double findStudentGrade (double key, int low, int high){
        double index = 0;
        while (low <= high) {
            int mid = low  + ((high - low) / 2);
            if (roster[mid].getAverage() < (key)) {
                low = mid + 1;
            } 
            else if (roster[mid].getAverage() > (key)) {
                high = mid - 1;
            } 
            else if (roster[mid].getAverage() == (key)) {
                index = mid;
                break;
            }
        }
        return index;
    }

    //utility methods here

    public static Student[] loadFile (String filename){

        //file format has class size as first value in file
        int n;
        Student[] temp;

        try{
            Scanner f = new Scanner (new File (filename));
            n = f.nextInt();
            f.nextLine();

            temp = new Student [n];

            for (int i = 0; i < temp.length; i++){
                String line = f.nextLine();
                String[] t = line.split(",");
                temp[i] = new Student (t[0], Double.parseDouble (t[1]));
            }
            f.close();
            return temp;
        }
        catch (Exception e){
            System.out.println (e.getMessage());
            return null;
        }

    }
    private void sort(){
        int n = Course.roster.length; //get the length

        int gap = n; //start the gap being the size of the array

        boolean swapped = true; //flag for if elements have swapped

        //while we're not at a gap of 1 and swaps have been made
        while (gap != 1 || swapped){

            gap = nextGap(gap); //get the next gap value

            swapped = false; //set the swapped flag

            for (int i = 0; i < n-gap; i++){ //iterate through the array by the gap size
                if (roster[i].getAverage() > roster[i + gap].getAverage()){ //if the current element is larger than the element 1 gap ahead 
                    swap (roster, i, i+gap); //swap them
                    swapped = true;
                }
            }
        }

    }
    //utility method for calculating the gap size
    private int nextGap (int g){
        g = (g*10)/13; //calculate the gap shrink factor (1.3)
        if (g < 1){ //if we're below 1, the gap is 1
            return 1;
        }
        else{
            return g;
        }
    }

    //utility method for swapping elements
    private void swap(Student[] a, int i, int j){
        Student temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

}
