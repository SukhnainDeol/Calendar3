// Programmer: Sukhnain Deol
// Date: 11/15/22
// Class: Computer Science 141
// Assignment: Calendar Part 3

// Program Function:
    //

// Extra credit: 
    //

// Bugs/Problems:
    //

// To-Do
    //

// Done
    // 


  import java.util.Calendar; // used for functions that need to access specific calendar information
  import java.time.LocalDate; // used to access current dates for calendar
  import java.util.Scanner; // allows access to user input
  
  public class SDeolCalendar3
  {
    public static void main(String[] args)
    {
        String[][] eventArr = new String[12][31];

        Scanner in = new Scanner(System.in); // creates scanner object to access inputs

        LocalDate date = LocalDate.now();// java.date.LocalDate library creates calendar object

        String dateInput = "00/00"; // input of date storage (placeholder value)
        
        int inputDay = 0; // holds input of user for day
        int inputMonth = 0; // holds input of user for month
        int monthLength = 0; // hold the length of month (e.g. january is 31 days)
        int calendarSize = 5; // placeholder value for size of calendar which is inputted by user
        int currentMonth = date.getMonthValue();
        int currentDay = date.getDayOfMonth();
        
        boolean calendarIsOpen = false; // check if calendar is open before being able to quit or go next/previous
        boolean calendarInUse = true; // turns off calendar loop when 'q' is inputted in menu
        boolean correctMenuInput = true; // keeps asking for menu input until valid one is given
        boolean realDateInputted = false; // used to check if user gives a real date on a calendar
        boolean isLeapYear;  // lets program know if to calculate based on a leap year 
        boolean isSizeChosen = false; // checks if need to ask user for calendar size first time
  
        // checks if its a leap year
        if (date.getYear()% 4 == 0) // if leap year
        {isLeapYear = true;}
        else // if not leap year
        {isLeapYear = false;}

        while (calendarInUse) // calendar program loop
        {
        do // menu input loop
        {
            correctMenuInput = true; // assumes correct input, declares false if incorrect input found

            System.out.println("Please Enter One Of The Following Commands");
            System.out.println("'e' to display an inputted date");
            System.out.println("'t' to display current date");
            System.out.println("'n' to display next month");
            System.out.println("'p' to display previous month");
            System.out.println("'s' to change calendar size");
            System.out.println("'q' to quit the program");
        
            String menuInput = in.next().toLowerCase(); // takes in next token inputted in lower case
            in.nextLine(); // clears inputs

            switch(menuInput) 
            {
            case "e": // user enters a date to print

                realDateInputted = false; 
                // repeatedly asks for input until valid one is given
                while(realDateInputted == false)
                {
                    // asks for date input and stores (mm/dd)
                    System.out.println("Please Enter a Date (mm/dd): ");
                    dateInput = in.nextLine(); // takes in mm/dd input

                    // checks if input is formatted correclty and that the date is real
                    if (dateInput.length() == 5 && dateInput.charAt(2) == '/' && isRealDate(dateInput, isLeapYear, in)) 
                        {realDateInputted = true;} // ends loop
                    else
                        {System.out.println("\nERROR: Invalid Input\n");}
                }

                // seperates input into month and day
                inputDay = dayFromDate(dateInput);
                inputMonth = monthFromDate(dateInput);

                // sets length of month
                monthLength = findMonthLength(inputMonth); 

                calendarIsOpen = true; // states calendar is opened 

                break;
            case "t": // prints today's date
                // seperates input into month and day
                inputDay = currentDay;
                inputMonth = currentMonth;

                monthLength = findMonthLength(currentMonth);

                calendarIsOpen = true; // states calendar is opened 
                
                break;
            case "q": // quit
                if (calendarIsOpen) // if calendar is open
                    {calendarInUse = false;} // marks calendar to be closed
                else  // if calendar isnt  open
                {
                    System.out.println("\nERROR:Please open the calendar before quitting.\n");
                    correctMenuInput = false; // keeps loop going till correct input
                }

                break;
            case "n": // print next month
                if (calendarIsOpen) // if calendar is open
                {
                    if(inputMonth < 12) // if calendar is less than 12 add 1
                        {inputMonth++;}
                    else
                        {inputMonth = 1;} // calendar is 12 or higher, go to month 1

                    monthLength = findMonthLength(inputMonth);// changes calendar length to fit month
                }
                else  // if calendar isnt  open
                {
                    System.out.println("\nERROR: Please open the calendar before changing it.\n");
                    correctMenuInput = false;
                }

                break;
            case "p": // print previous month
                if (calendarIsOpen) // if calendar is open
                {
                    if(inputMonth == 1) // if month is 1, goes back to 12
                        {inputMonth = 12;}
                    else // else go back one month
                        {inputMonth--;}

                    monthLength = findMonthLength(inputMonth);// changes calendar length to fit month
                }
                else // if calendar isnt open
                {
                    System.out.println("\nERROR: Please open the calendar before changing it.\n");
                    correctMenuInput = false; 
                }

                break;
            default:
                System.out.println("\nERROR: Invalid Input\n");
                correctMenuInput = false;
                
                break;
            case "s":
                if (calendarIsOpen) // if calendar is open
                {
                    calendarSize = askCalendarSize(in); // asks user for calendar size integer between 1-10
                    System.out.println("\nSize Set!\n"); // lets user know they inputted correctly
                    isSizeChosen = true; // doesnt require program to ask for size when printing something
                }
                else // if calendar isnt open
                    {System.out.println("\nERROR: Please open the calendar before changing it.\n");}

                correctMenuInput = false; // restarts loop to ask for input of what to print

                break;
            }
        } while (correctMenuInput == false); // end of menu do-while
        

        if (calendarInUse == false)
            {break;} // stops program loop if 'q' input is given
        
        if (isSizeChosen == false) // if user hasnt chosen size of calendar yet
        {
            calendarSize = askCalendarSize(in); // asks user for calendar size integer between 1-10
            isSizeChosen = true;
        } 

        int carLocation = 0; // used to determine where the car will be printed
        if (calendarSize >= 4) // if calendar is longer than car
            {carLocation = ((7*(calendarSize +2))-40)/2;} // length of space on each side of calendar if car is centered

        // Print Inputted Month Calendar 
        printCar(carLocation); // ascii art for calendar
        System.out.println();
        drawMonth(inputMonth, calendarSize, date, monthLength, inputDay); // prints inputted calendar month
        displayDate(inputMonth, inputDay); // prints inputted day and month seperately
        System.out.println(); 

        } // end of calendar loop 

        in.close(); // closes scanner to prevent resource leaks

    }// end of main method




    public static boolean isRealDate(String dateInput, boolean isLeapYear, Scanner in)
    {

        // tries following code, prints error if doesnt work
        try 
        {
            // checks if input is a real date on the calendar
            switch (monthFromDate(dateInput))
            { 
                // months with 31 days
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                // if day is between or equal to 1-31
                if (dayFromDate(dateInput) <=31 && dayFromDate(dateInput) >= 1)
                    {return true;}
                else 
                    {return false;}

                // months with 30 days
                case 4:
                case 6:
                case 9:
                case 11:
                // if day is between or equal to 1-30
                if (dayFromDate(dateInput) <= 30 && dayFromDate(dateInput) >= 1)
                    {return true;}
                else 
                    {return false;}

                // month with 29 days
                case 2:
                // if day is between or equal to 1-29 and leap year
                if (isLeapYear && dayFromDate(dateInput) <= 29 && dayFromDate(dateInput) >= 1 )
                    {return true;} 
                else if (isLeapYear == false && dayFromDate(dateInput) <= 28 && dayFromDate(dateInput) >= 1 )
                    {return true;}
                else 
                    {return false;}

                default:
                return false;


            } // end of month from date switch/case
        } // end of try
        catch(Exception e) // sends error if incorrect input
            {return false;}
        
    } // end of isRealDate



    // uses inputted month to find length of month
    public static int findMonthLength(int month)
    {
        Calendar calendar = Calendar.getInstance(); // creates calendar object
        calendar.set(Calendar.MONTH, month -1); // months in this function are from 0-11

        int monthLength = calendar.getActualMaximum(Calendar.DATE); // gets max number of days in month field of calendar

        return monthLength;
    }


    // asks user for what size they want the calendar to be
    public static int askCalendarSize(Scanner in)
    {
        boolean correctSizeType = false; // if correct input for size is given
        int calendarSize = 5;
            
        while (correctSizeType == false)
        {
            System.out.println("What size would you like your calendar (1-10)?: ");
            
            if (in.hasNextInt())// if value is an int between 1-10
            {
                calendarSize = in.nextInt();
                // if calendar size is between 1-10
                if (calendarSize <= 10 && calendarSize >= 1)
                    {correctSizeType = true;} // ends loop
                else
                    {System.out.println("ERROR: Invalid Input");}
            }
            else
            {
                System.out.println("ERROR: Invalid Input");
                in.nextLine(); 
            }
        }

        return calendarSize;
    }



    // prints a given number of a given character
    public static void addChar(String addChar, int amount)
    {
        for (int i = 0; i < amount; i++)
            {System.out.print(addChar);}
    }// end of addChar



    // Function: draws a 7x5 calendar with the month numnber on top
    public static void drawMonth(int month, int size, LocalDate date, int monthLength, int inputDay) 
    {
        int calendarLength = 7 *(size+2);
        int calendarHalfLength = calendarLength/2 - ((calendarLength %2)/2); // rounded down

        // prints current month in the middle of calendar's top 
        addChar(" ", calendarHalfLength);
        System.out.println(month); // prints month in center top
        
        // prints top border of equal signs 
        addChar("=", calendarLength);

        System.out.println(); // moves to next line
        
        int currentYear = date.getYear(); 
        int monthStartDay = monthStartDay(month, currentYear);

        int maxRows = 7; // max rows possible in calendar - 1
        int totalCalendarSlots = monthLength + monthStartDay -1; // total spaces taken up by calendar

        if (totalCalendarSlots > 35) 
            {maxRows = 7;}// if calendar needs 6 rows
        else if (totalCalendarSlots < 36 & totalCalendarSlots > 28 ) 
            {maxRows = 6;}// if calendar needs 5 rows
        else 
            {maxRows = 5;}// if calendar needs 4 rows

        // prints 7x5 calendar
        for (int i = 1; i < maxRows; i++)
            {drawRow(i, month, size, date, monthStartDay, monthLength, inputDay);} // draws 5 rows

    } // end of drawMonth



    // Function: prints a week of a month
    public static void drawRow(int row, int month, int size, LocalDate date, int monthStartDay, int monthLength, int inputDay)
    {
        int rowLength = 7 *(size+2);
        int boxLength = size + 1;
        int boxHeight = (((size + 2)/3) - (((size + 2)%3)/3)); // rounded down int value of of size +2
        
        int boxDayNum = 0-monthStartDay+1 + 7*(row-1);


        // prints top layer of row
        for(int k = (row * 7 - 6); k <= 7*row; k++)
        {
            int printedDay = k - monthStartDay + 1; // actual day printed on console

            System.out.print("|"); // end of square

            if (printedDay <= monthLength && k >= monthStartDay)
                {System.out.print(printedDay);} // print date
            else if (k < monthStartDay)
                {System.out.print(" ");}
            else if (k > monthLength) 
                {System.out.print("  ");} // print blank space (if date not on calendar)
                


            if (printedDay < 10) // takes account for extra digit space
                {addChar(" ", boxLength-1);} // single digit
            else
                {addChar(" ", boxLength-2);}  // double digit (removes one space)

        }

        System.out.println("|"); // prints end of calendar and goes to next line
        // prints empty rows
        for (int i = 0; i < boxHeight; i++)
        {
            for (int l = 0; l < 7; l++)
            {
                System.out.print("|");
                boxDayNum++;

                if (boxDayNum == inputDay)
                {
                    addChar(" ",(boxLength/2 -1));
                    System.out.print("*");
                    addChar(" ", boxLength/2 + ((size-1) % 2));
                }
                else
                    {addChar(" ",boxLength);}

                if (l == 6) 
                    {System.out.print(("|"));}
            }
            System.out.println();
            boxDayNum -= 7;
        }

        // prints bottom border of equal signs 
        addChar("=", rowLength);

        System.out.println();
    } // end of drawRow



    // takes in year and month and outputs the starting day (e.g. 1 for Sunday, 7 for Saturday) of that month in that year
    public static int monthStartDay(int month, int year)
    {
        Calendar date = Calendar.getInstance(); // creates calendar object

        date.set(date.MONTH, month-1); // sets month to inputted, -1 because its 0-11
        date.set(date.DAY_OF_MONTH, 1); // sets day of month to the first one

        return date.get(date.DAY_OF_WEEK); // finds the day the first of the month is
    } // end of monthStartDay



    // prints inputted date
    public static void displayDate(int month, int day) 
    {
        // prints day and month each on separate lines 
        System.out.println("\nMonth: " + month); // inputted month
        System.out.println("Day: " + day); // inputted day
    }// end of displayDate



    // get month int from a date (mm/dd)
    public static int monthFromDate(String date)
    {
        // stores month portion of date as integer
        int inputMonth = Integer.parseInt(date.substring(0, 2)); 
        
        if (inputMonth < 10) // if multi digit
            {return inputMonth;} // return normal
        else // if single digit
            {return inputMonth / 1;} // removes leading 0
    } // end of monthFromDate



    // get day int from date (mm/dd)
    public static int dayFromDate(String date)
    {
        // stores day portion of date as integer
        int inputDay = Integer.parseInt(date.substring(3, 5)); 
        
        if (inputDay < 10) // if multi digit
            {return inputDay;} // return normal
        else // if single digit
            {return inputDay / 1;} // removes leading 0    
    } // end of dayFromDate



    //prints a car with a changeable location
    public static void printCar(int distanceFromEdge)
    { 
        // moves car location
        distanceFromEdge += 8; // must be postive
        // top of car 
        addChar(" ", distanceFromEdge);
        addChar("_", 24);

        // 2nd layer, starting edges and windows
        System.out.println();
        addChar(" ", distanceFromEdge + 1);
        System.out.print("/"); // edge
        // top of window
        addChar(" ", 4);
        addChar("_", 4);
        addChar(" ", 5);
        addChar("_", 4);
        addChar(" ", 3);
        System.out.println("\\"); // edge & ends line

        // 3rd layer, continuing edge and windows
        addChar(" ", distanceFromEdge);
        System.out.print("/");
        addChar(" ", 4);
        System.out.print("/");
        addChar(" ", 4);
        System.out.print("|");
        addChar(" ", 4);
        System.out.print("|");
        addChar(" ", 3);
        System.out.print("|");
        addChar(" ", 3);
        System.out.println("\\"); // edge & ends line

        // 4th layer stars body of car
        addChar(" ", distanceFromEdge - 7);

        addChar("_", 6);
        System.out.print("/");


        addChar(" ", 4);
        System.out.print("/");

        addChar("_", 5);
        System.out.print("|");

        addChar(" ", 4);
        System.out.print("|");
        addChar("_", 3);
        System.out.print("|");
        addChar(" ", 4);
        System.out.print("\\");
        addChar("_", 6);
        System.out.println();

        //5th layer doors handles and light
        addChar(" ", distanceFromEdge - 7);
        System.out.print("|");
        addChar(" ", 14);
        System.out.print("-");
        addChar(" ", 7);
        System.out.print("-");
        addChar(" ", 11);
        addChar("_", 3);
        System.out.print("\\");

        System.out.println();

        // 6th layer start of tires and end of lights
        addChar(" ", distanceFromEdge - 7);
        System.out.print("|");
        addChar(" ", 8);
        addChar("_", 4);
        addChar(" ", 11);
        addChar("_", 4);
        addChar(" ", 7);

        System.out.print("\\");
        System.out.print("_");
        System.out.print("/");
        System.out.print("|");
        System.out.println();

        //7th layer end of body, continue tires
        addChar(" ", distanceFromEdge - 7);
        System.out.print("|");
        addChar("_", 7);
        System.out.print("/");
        addChar(" ", 4);
        System.out.print("\\");
        addChar("_", 9);
        System.out.print("/");
        addChar(" ", 4);
        System.out.print("\\");
        addChar("_", 9);
        System.out.print("/");
        System.out.println();

        // 8th layer 
        addChar(" ", distanceFromEdge + 1);
        System.out.print("\\");
        addChar("_", 4);
        System.out.print("/");
        addChar(" ", 9);

        System.out.print("\\");
        addChar("_", 4);
        System.out.print("/");
        addChar(" ", 9);
        System.out.println();
    }// end of printCar
    }// end of class