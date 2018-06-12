package commalindah.httpsgithub.markrecorder;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by l.huang on 5/24/2018.
 */

public class Student implements Parcelable {
    private String firstName;
    private String lastName;
    private String mark;

    /**
     * Student sets class-level variables firstName, lastName, and mark to be empty strings
     *
     * @param "" There are no parameters
     * @return Nothing is returned
     */
    public Student()
    {
        firstName = "";
        lastName = "";
        mark = "";
    }

    /**
     * Student sets class-level variables firstName, lastName, and mark to their corresponding parameters
     *
     * @param firstNameInput is the input of firstName
     * @param lastNameInput is the input of lastName
     * @param markInput is the input of mark
     * @return Nothing is returned
     */
    public Student( String firstNameInput, String lastNameInput, String markInput )
    {
        firstName = firstNameInput;
        lastName = lastNameInput;
        mark = markInput;
    }

    /**
     * Student gets the students' information from the parcel by calling the readFromParcel method
     *
     * @param in is the parcel of students
     * @return Nothing is returned
     */
    public Student(Parcel in) {
        readFromParcel(in);
    }

    /**
     * getFirstName is the method used to get the first name of a student
     *
     * @param "" There are no parameters
     * @return a string of students' first names
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * getLastName is the method used to get the last name of a student
     *
     * @param "" There are no parameters
     * @return a string of students' last names
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * getMark is the method used to get the mark of a student
     *
     * @param "" There are no parameters
     * @return a string of students' marks
     */
    public String getMark()
    {
        return mark;
    }

    /**
     * describeContents describes the contents in the parcel
     *
     * @param "" There are no parameters
     * @return an integer of 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * writeToParcel writes students' information to a string array
     *
     * @param parcel contains the string array of students' information
     * @param i is an integer i
     * @return Nothing is returned
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        String[] studentInfo = new String[]{ firstName, lastName, mark };
        parcel.writeStringArray( studentInfo );
    }

    /**
     * readFromParcel reads students' information from the string array
     *
     * @param in is the parcel of students
     * @return Nothing is returned
     */
    private void readFromParcel(Parcel in) {
        String[] studentInfo = new String[3];

        in.readStringArray(studentInfo);

        firstName = studentInfo[0];
        lastName = studentInfo[1];
        mark = studentInfo[2];
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        /**
         * createFromParcel creates a new Student from the parcel
         *
         * @param parcel contains students' information
         * @return the new Student
         */
        @Override
        public Student createFromParcel(Parcel parcel) {
            return new Student(parcel);
        }

        /**
         * newArray creates a new student array
         *
         * @param size is the size of the array
         * @return the new student array
         */
        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
