import java.io.StringReader
import java.sql.Date
import java.time.LocalDate

abstract class Person(){
    var name:String =""
    var age :Int =0
    var Email:String=""
    var address:String=""
    abstract fun searchAndGet(num:Int)
    abstract fun edit(Id:Int)
    abstract fun remove(Id:Int)

    companion object{
        private var cn=0
        val EnrolledCourses:MutableMap<String ,String> = mutableMapOf()
        val courses: MutableMap<String, String> = mutableMapOf()
    }
    var id :Int= cn++

    fun enrollInCourse(courseCode: String) {
        if(courses.isEmpty()){
            println("NO Courses added yet...")
            return
        }
        val courseName = courses[courseCode]
        if (courseName != null) {
            EnrolledCourses[courseCode] = courseName
            println("Enrolled in course: $courseName")
        } else {
            println("Course not found")
        }
    }
}

interface validation{
    val registerEmails: MutableList<String>
    var isLoggedIn: Boolean

    fun isEmailUnique(email:String):Boolean{
        return email !in registerEmails
    }
    fun isValidEmail(email :String):Boolean{
        val emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return  emailPattern.matches(email)
    }

    fun login(email:String)

}

class Student:Person(),validation{
    var gpa:Float =0.0f
    var Faculty :String=""

    private val addStudent :MutableList<Student> = mutableListOf()
    override val registerEmails: MutableList<String>
            = mutableListOf()
    override var isLoggedIn: Boolean=false

    fun DispalycoursesEnrolled(){
        if(EnrolledCourses.isEmpty()){
            println("There is NO courses enrolled yet.....")
            return
        }
        for(it in EnrolledCourses){
            println("Course code : ${it.key}\t  courseName : ${it.value}" )
        }
    }

    fun signupasStu(){

        val Student1=Student()

        println("Enter your name : ")
        Student1.name=readLine() ?: return
        println("Enter your age : ")
        Student1.age=readln().toIntOrNull() ?: return
        println("Enter your Email : ")
        Student1.Email=readLine() ?: return
        while( !isValidEmail( Student1.Email) || !isEmailUnique( Student1.Email) ){
            if(!isEmailUnique( Student1.Email)){
                println("Email already exists in the system......try to Enter another one")
            }else if(!isValidEmail( Student1.Email))
            {
                println("Invalid Email. Please try again:")
            }
            Student1.Email=readLine() ?: return
        }
        println("Enter Your address ")
        Student1.address=readln()
        println("Enter your gpa : ")
        Student1.gpa= readln().toFloatOrNull() ?: return
        println("Enter your Faculty : ")
        Student1.Faculty=readLine()?: return
        println("Register completed successfully")
        registerEmails.add(Student1.Email)
        addStudent.add(Student1)

    }

    override fun login(email: String) {
        val student = addStudent.find { it.Email == email }
        if (student != null) {
            println("Login successful!")
            isLoggedIn=true;
        } else {
            println("Student with the provided email not found.")
            isLoggedIn=false
        }
    }

  fun viewStudent() {
      var i = 1
      if (addStudent.isEmpty()) {
          println("No data entered yet!")
          return
      }

      val horizontalLine = "+-----------+-----------------+-----+-------------------+----------------------+-----+--------------------+"
      val header = "No. | Student ID |      Name       | Age |       Email       |       Address        | GPA |      Faculty      |"

      println(horizontalLine)
      println(header)
      println(horizontalLine)

      for (student in addStudent) {
          val studentData = "%3d | %10s | %15s | %3s | %18s | %21s | %.2f | %10s |".format(
              i,
              student.id,
              student.name,
              student.age.toString(),
              student.Email,
              student.address,
              student.gpa,
              student.Faculty
          )
          println(studentData)
          i++
      }

      println(horizontalLine)
  }
    override fun searchAndGet(num: Int) {
        for(search in addStudent){
            if(search.id==num){
                println("__________________________________________________")
                println("Student with ID ${search.id} Founded ")
                println("Student Details for Student ID (${search.id})")
                println("student name : ${search.name}\nstudent Age : ${search.age}\nstudent address : ${search.address}")
                println("student gpa : ${search.gpa}\nstudent Faculty : ${search.Faculty}")
                println("__________________________________________________")

                return
            }
        }
        println("Sorry...Student with ID ${num} Not Founded In System")

    }

    override fun edit(num:Int) {
        val found=addStudent.find { it.id==num }
        if(found !=null){

            println("Edit Student Data:")
            println("   1. Name: ${found.name}")
            println("   2. Age: ${found.age}")
            println("   3. Email: ${found.Email}")
            println("   4. GPA: ${found.gpa}")
            println("   5. Faculty: ${found.Faculty}")
            println("   6. address: ${found.address}")
            println("Select the number of the field you want to edit:")
            val choise = readln().toInt()
            when(choise){
                1->{
                    println("Enter The New Name : ")
                    val newName= readln().toString()
                    if(!newName.isNullOrEmpty()){
                        found.name=newName
                        println("Name updated successfully.")
                    }else{
                        println("Invalid input. Name not updated.")
                    }
                }
                2->{
                    println("Enter The New age : ")
                    val newage= readln().toInt()
                    if(newage >0 && newage!=null){
                        found.age=newage
                        println("Age updated successfully.")
                    }else{
                        println("Invalid input. Age not updated.")
                    }
                }
                3->{
                    println("Enter The New Email : ")
                    val newEmail= readln()
                    if(isEmailUnique(newEmail) && isValidEmail(newEmail) && !newEmail.isNullOrEmpty()){
                        registerEmails.remove(found.Email)
                        found.Email=newEmail
                        registerEmails.add(newEmail)
                        println("Email updated successfully.")
                    }else{
                        println("Invalid input. Email not updated.")
                    }
                }
                4->{
                    println("Enter The New GPA : ")
                    val newGpa= readln().toFloat()
                    if (newGpa >=0 && newGpa!=null){
                        found.gpa=newGpa
                        println("GPA updated successfully.")
                    }else
                    {
                        println("Invalid input. Gpa not updated.")
                    }
                }
                5->{
                    println("Enter The New Faculty : ")
                    val newFaculty = readln()
                    if(!newFaculty.isNullOrEmpty()){
                        found.Faculty=newFaculty
                        println("Faculty updated successfully.")
                    }else{
                        println("Invalid input. Faculty not updated.")

                    }
                }
                6->{
                    println("Enter The New address : ")
                    val newAddress = readln()
                    if(!newAddress.isNullOrEmpty()){
                        found.address=newAddress
                        println("Faculty updated successfully.")
                    }else{
                        println("Invalid input. Faculty not updated.")

                    }
                }
                else-> println("Invalid field choice. No data updated.")
            }

        }else{
            println("Student with ID $num not found in the system.")
        }
    }

    override fun remove(studentId: Int) {
        if(addStudent.isEmpty()){
            println("List Of Students Is Empty ")
            return
        }
        val studentToRemove = addStudent.find { it.id == studentId }

        if (studentToRemove != null) {
            addStudent.removeIf { it.id == studentId }
            registerEmails.remove(studentToRemove.Email)
            println("Student with ID $studentId removed successfully")
        } else {
            println("Student with ID $studentId not found in the system")
        }
    }

}

class Instructor :Person(),validation {
    var salary: Double = 0.0
    var CoursesTaught: String = ""
    var codecourse: String = ""

    private val Instructors: MutableList<Instructor> = mutableListOf()

    override val registerEmails: MutableList<String> = mutableListOf()
    override var isLoggedIn: Boolean=false

    fun SignAsInstructor() {
        val Instructor1 = Instructor()
        println("Enter Your Name : ")
        Instructor1.name = readLine() ?: return
        println("Enter Your Age : ")
        Instructor1.age = readln().toIntOrNull() ?: return
        println("Enter Your Email : ")
        Instructor1.Email = readLine() ?: return
        while (!isValidEmail(Instructor1.Email) || !isEmailUnique(Instructor1.Email)) {
            if (!isEmailUnique(Instructor1.Email)) {
                println("Email already exists in the system......try to Enter another one")
            } else if (!isValidEmail(Instructor1.Email)) {
                println("Invalid Email. Please try again:")
            }
            Instructor1.Email = readLine() ?: return
        }
        println("Enter Your Salary : ")
        Instructor1.salary = readln().toDoubleOrNull() ?:return
        println("Enter Your Address : ")
        Instructor1.address = readLine() ?: return

        var validCourseCode = false

        while (!validCourseCode) {
            println("Enter The name of the course You Will give: ")
            Instructor1.CoursesTaught = readLine() ?: return

            println("Enter Your Course Code: ")
            Instructor1.codecourse = readLine() ?: return

            if (courses.containsKey(Instructor1.codecourse)) {
                println("Course code already exists. Please enter a different one.")
            } else {
                validCourseCode = true
            }
        }

        Instructors.add(Instructor1)
        registerEmails.add(Instructor1.Email)
        courses[Instructor1.codecourse] = Instructor1.CoursesTaught
        println("\tRegister completed successfully")
    }
    fun DisplayCourses(){
        for(it in courses){
            println("Code : ${it.key}\tcourseName : ${it.value}" )
        }
    }

    fun enrolled(){
        println("Enter The Code Of Course You Want To Rigister In : ")
        val code1= readln()
        enrollInCourse(code1)
    }

    fun ViewDAta() {
        var i = 1
        if (Instructors.isEmpty()) {
            println("\tThere are no instructors yet!")
            return
        }

        val horizontalLine = "+------+-----------------+-----+-------------------+-----------------+----------------------+----------------------+------------------+"
        val header = "| No.  |   Instructor ID  | Age |       Name        |      Email      |       Salary         |       Address        |    Course Taught   |"

        println(horizontalLine)
        println(header)
        println(horizontalLine)

        for (instructor in Instructors) {
            val instructorData = "| %4d | %-15s | %3d | %-17s | %-15s | %20 | %-20s | %-18s |".format(
                i,
                instructor.id,
                instructor.age,
                instructor.name,
                instructor.Email,
                instructor.salary,
                instructor.address,
                instructor.CoursesTaught
            )
            println(instructorData)
            i++
            println(horizontalLine)
        }
    }

    override fun login(email: String) {
        val instructor = Instructors.find { it.Email == email }
        if (instructor != null) {
            println("Login successful!")
            isLoggedIn=true;

        } else {
            println("\tinstructor with the provided email not found.")
            isLoggedIn=false
        }
    }
    override fun searchAndGet(num: Int) {
        val Found = Instructors.find { it.id == num }
        if (Found != null) {
            println("Instructor With Id ${Found.id} Founded ")
            println("Name : ${Found.name}")
            println("age : ${Found.age}")
            println("Email : ${Found.Email}")
            println("salary : ${Found.salary}")
            println("address : ${Found.address}")
            println("His course : ${Found.CoursesTaught}         code : ${Found.codecourse}")
        } else {
            println("\tInstructor of Id ${num} Not founded in System !!")
        }
    }


    override fun edit(Id: Int) {
        val found = Instructors.find { it.id == Id }
        if (found != null) {

            println("Edit Student Data:")
            println("   1. Name: ${found.name}")
            println("   2. Age: ${found.age}")
            println("   3. Email: ${found.Email}")
            println("   4. Salary: ${found.salary}")
            println("   5. address: ${found.address}")
            println("   6. Course: ${found.CoursesTaught}")
            println("   7. Coursecode: ${found.codecourse}")

            println("Select the number of the field you want to edit (1/7) :")
            val choise = readln().toInt()
            when (choise) {
                1 -> {
                    println("Enter The New Name : ")
                    val newName = readLine() ?: return
                    if (!newName.isNullOrEmpty()) {
                        found.name = newName
                        println("\tName updated successfully.")
                    } else {
                        println("\tInvalid input. Name not updated.")
                    }
                }

                2 -> {
                    println("Enter The New age : ")
                    val newage = readln().toIntOrNull() ?: return
                    if (newage > 0 && newage != null) {
                        found.age = newage
                        println("\tAge updated successfully.")
                    } else {
                        println("\tInvalid input. Age not updated.")
                    }
                }

                3 -> {
                    println("Enter The New Email : ")
                    val newEmail =readLine() ?: return
                    if (isEmailUnique(newEmail) && isValidEmail(newEmail) && !newEmail.isNullOrEmpty()) {
                        registerEmails.remove(found.Email)
                        found.Email = newEmail
                        registerEmails.add(newEmail)
                        println("\tEmail updated successfully.")
                    } else {
                        println("\tInvalid input. Email not updated.")
                    }
                }

                4 -> {
                    println("Enter The New salary : ")
                    val salary = readln().toDoubleOrNull()?:return
                    if (salary >= 0 && salary != null) {
                        found.salary = salary
                        println("\tSalary updated successfully.")
                    } else {
                        println("\tInvalid input. Salary not updated.")
                    }
                }

                5 -> {
                    println("Enter The New Adrress : ")
                    val newadaress = readLine() ?: return
                    if (!newadaress.isNullOrEmpty()) {
                        found.address = newadaress
                        println("\taddress updated successfully.")
                    } else {
                        println("\tInvalid input. address not updated.")

                    }
                }
                6 -> {
                    println("Enter The New courseName: ")
                    val newCourse = readLine() ?: return
                    if (!newCourse.isNullOrEmpty()) {
                        val oldCourse = found.CoursesTaught
                        val oldCode = found.codecourse
                        found.CoursesTaught = newCourse
                        courses[oldCode] = newCourse

                        println("\tCourse taught successfully updated!")
                    } else {
                        println("\tInvalid input. Course not updated.")
                    }
                }

                7 -> {
                    println("Enter The New code of course: ")
                    val newCode = readLine() ?: return
                    if (!newCode.isNullOrEmpty()) {
                        val oldCourse = found.CoursesTaught
                        val oldCode = found.codecourse

                        // Remove the old course from the courses map
                        courses.remove(oldCode)

                        found.codecourse = newCode

                        // Add the updated course to the courses map
                        courses[newCode] = oldCourse

                        println("\tCourse Code successfully updated!")
                    } else {
                        println("\tInvalid input. Code of course not updated.")
                    }
                }
                else -> println("\tInvalid field choice. No data updated.")
            }

        } else {
            println("\tStudent with ID $Id not found in the system.")
        }
    }

    override fun remove(Id: Int) {
        if (Instructors.isEmpty()) {
            println("List Of Instructor Is Empty ")
            return
        }
        val removeInstructor = Instructors.find { it.id == Id }
        if (removeInstructor != null) {
            Instructors.remove(removeInstructor)
            registerEmails.remove(removeInstructor.Email)
            courses.remove(removeInstructor.codecourse)
            println("\tInstructor with ID ${removeInstructor.id} removed successfully")
        } else {
            println("\tInstructor with ID ${Id} not found in the system To Remove ")
        }
    }
}

class Employee:Person() ,validation{
    var Salary :Double=0.0
    lateinit var hireDate:LocalDate

    private val employees:MutableList<Employee> = mutableListOf()
    override val registerEmails: MutableList<String> = mutableListOf()
    override var isLoggedIn: Boolean=false

    override fun login(email: String) {
        val employee = employees.find { it.Email == email }
        if (employee != null) {
            println("\tLogin successful!")
            isLoggedIn=true
        } else {
            println("\tEmployee with the provided email not found.")
            isLoggedIn=false
        }
    }

    fun SignUp(){
        val emp=Employee()
        println("Enter your name : ")
        emp.name=readLine() ?: return
        println("Enter your age : ")
        emp.age=readln().toIntOrNull() ?: return
        println("Enter your Email : ")
        emp.Email=readLine() ?: return
        while( !isValidEmail( emp.Email) || !isEmailUnique( emp.Email) ){
            if(!isEmailUnique( emp.Email)){
                println("Email already exists in the system......try to Enter another one")
            }else if(!isValidEmail( emp.Email))
            {
                println("Invalid Email... Please try again:")
            }
            emp.Email=readLine() ?: return
        }
        println("Enter Your salary : ")
        emp.Salary= readln().toDoubleOrNull()?:return
        println("Enter Your address : ")
        emp.address= readln()
        emp.hireDate= LocalDate.now()
        employees.add(emp)
        registerEmails.add(emp.Email)
        println("\tRegister completed successfully")
    }
    fun viewdata() {
        var i = 1
        if (employees.isEmpty()) {
            println("There are no employees yet!")
            return
        }
        val horizontalLine = "+------+----------------+-----+-------------------+----------------------+-----------------+---------------------+---------------------"
        val header = "| No.  |   Employee ID  | Age |       Name        |       Email          |      Address      |      Salary         |    Hire Date     |"
        println(horizontalLine)
        println(header)
        println(horizontalLine)
        for (employee in employees) {
            val employeeData = "| %4d | %-14s | %3d | %-17s | %-21s | %-15s | %,17.2f | %-17s |".format(
                i,
                employee.id,
                employee.age,
                employee.name,
                employee.Email,
                employee.address,
                employee.Salary,
                employee.hireDate
            )
            println(employeeData)
            i++
            println(horizontalLine)
        }
    }

    override fun searchAndGet(num: Int) {
        val Found = employees.find { it.id == num }
        if (Found != null) {
            println("Employee With Id ${Found.id} Founded ")
            println("Name : ${Found.name}")
            println("age : ${Found.age}")
            println("Email : ${Found.Email}")
            println("salary : ${Found.Salary}")
            println("address : ${Found.address}")
            println("hireDate : ${Found.hireDate}")
        } else {
            println("\tEmployee of Id ${num} Not founded in System !!")
        }
    }

    override fun edit(Id: Int) {
        val found = employees.find { it.id == Id }
        if (found != null) {

            println("Edit Student Data:")
            println("   1. Name: ${found.name}")
            println("   2. Age: ${found.age}")
            println("   3. Email: ${found.Email}")
            println("   4. Salary: ${found.Salary}")
            println("   5. address: ${found.address}")
            println("Select the number of the field you want to edit (1/5) :")
            val choise = readln().toInt()
            when (choise) {
                1 -> {
                    println("Enter The New Name : ")
                    val newName =readLine() ?: return
                    if (!newName.isNullOrEmpty()) {
                        found.name = newName
                        println("\tName updated successfully.")
                    } else {
                        println("\tInvalid input. Name not updated.")
                    }
                }

                2 -> {
                    println("Enter The New age : ")
                    val newage = readln().toIntOrNull()?:return
                    if (newage > 0 && newage != null) {
                        found.age = newage
                        println("\tAge updated successfully.")
                    } else {
                        println("\tInvalid input. Age not updated.")
                    }
                }

                3 -> {
                    println("Enter The New Email : ")
                    val newEmail = readLine() ?: return
                    if (isEmailUnique(newEmail) && isValidEmail(newEmail) && !newEmail.isNullOrEmpty()) {
                        registerEmails.remove(found.Email)
                        found.Email = newEmail
                        registerEmails.add(newEmail)
                        println("\tEmail updated successfully.")
                    } else {
                        println("\tInvalid input. Email not updated.")
                    }
                }

                4 -> {
                    println("Enter The New salary : ")
                    val salary = readln().toDoubleOrNull()?:return
                    if (salary >= 0 && salary != null) {
                        found.Salary = salary
                        println("\tSalary updated successfully.")
                    } else {
                        println("\tInvalid input. Salary not updated.")
                    }
                }

                5 -> {
                    println("Enter The New Adrress : ")
                    val newadaress = readLine() ?: return
                    if (!newadaress.isNullOrEmpty()) {
                        found.address = newadaress
                        println("\taddress updated successfully.")
                    } else {
                        println("\tInvalid input. address not updated.")

                    }
                }

                else -> println("\tInvalid field choice. No data updated.")
            }

        } else {
            println("\tEmployee with ID $Id not found in the system.")
        }
    }

    override fun remove(Id: Int) {
        if (employees.isEmpty()) {
            println("\tList Of Employees Is Empty... ")
            return
        }
        val removeEmployee = employees.find { it.id == Id }
        if (removeEmployee != null) {
            employees.remove(removeEmployee)
            registerEmails.remove(removeEmployee.Email)
            println("\tEmployee with ID ${removeEmployee.id} removed successfully..")
        } else {
            println("\tEmployee with ID ${Id} not found in the system To Remove ")
        }
    }
}

class Sysytem{
    //_________________________________________ Student functions__________________________________

    private val Stu=Student()
    private val Ins=Instructor()
    private val emp=Employee()
    fun addStudent(){
        Stu.signupasStu()
    }
    fun ListOfStudent(){
        Stu.viewStudent()
    }
    fun SearchStudentThenGet(){
        println("Enter The Student Id You Want to search About : ")
        var stid:Int= readln().toInt()
        Stu.searchAndGet(stid)
    }
    fun RemoveStudent(){
        println("Enter Student Id You Want To delete : ")
        var Stid:Int= readln().toInt()
        Stu.remove(Stid)
    }
    fun UpdataStudentData(){
        println("Enter Your Id To Edit Your Data : ")
        val stid= readln().toInt()
        Stu.edit(stid)
    }
    fun enrollCourse(){
        Ins.enrolled()
    }
    fun Dispaly_Student_Enrolled(){
        Stu.DispalycoursesEnrolled()
    }
    fun LOgin(){
        println("Enter Your Email : ")
        var Em= readLine() ?:return
        Stu.login(Em)
    }
    fun Islogin():Boolean{
        return Stu.isLoggedIn==true
    }
    fun minu1(){
        println("\t1-Show Student List ")
        println("\t2-Get specific Student data ")
        println("\t3-Update data ")
        println("\t4-Enroll in course ")
        println("\t5-Show courses Enrolled ")
        println("\t6-remove Student")
        println("\t7-Go Back")
        println("\t8-Exit")

    }
    //_________________________________________ Instructor functions__________________________________
    fun addInstructor(){
        Ins.SignAsInstructor()
    }
    fun ListOfInstructors(){
        Ins.ViewDAta()
    }
    fun searchInstructorThenGet(){
        println("Enter The Instructor Id You Want to search About : ")
        var Sid:Int= readln().toInt()
        Ins.searchAndGet(Sid)
    }
    fun RemoveInstructor(){
        println("Enter Instructor Id You Want To delete : ")
        var instructorId:Int= readln().toInt()
        Ins.remove(instructorId)
    }
    fun UpdateInstructorData(){
        println("Enter Your Id To Edit Your Data : ")
        val instructorId= readln().toInt()
        Ins.edit(instructorId)
    }
    fun Display_Course(){
        Ins.DisplayCourses()
    }
    fun LOgin2(){
        println("Enter Your Email : ")
        var Em= readLine() ?:return
        Ins.login(Em)
    }
    fun Islogin2():Boolean{
        return Ins.isLoggedIn==true
    }
    fun minu2(){
        println("\t1-Show Instructors List ")
        println("\t2-Get specific Instructor data ")
        println("\t3-Update data ")
        println("\t4-Display Courses List")
        println("\t5-remove instructor")
        println("\t6-Go Back")
        println("\t7-Exit")
    }
    //_________________________________________ Employee functions__________________________________
    fun addEmployee(){
        emp.SignUp()
    }
    fun ListOfEmp(){
        emp.viewdata()
    }
    fun searchEmployeeThenGet(){
        println("Enter The Employee Id You Want to search About : ")
        var eid:Int= readln().toInt()
        emp.searchAndGet(eid)
    }
    fun RemoveEmployee(){
        println("Enter Student Id You Want To delete : ")
        var employee1:Int= readln().toInt()
        emp.remove(employee1)

    }
    fun UpdateEmployeeData(){
        println("Enter Your Id To Edit Your Data : ")
        var employee1:Int= readln().toInt()
        emp.edit(employee1)
    }
    fun LOgin3(){
        println("Enter Your Email : ")
        var Em= readLine() ?:return
        emp.login(Em)
    }
    fun Islogin3():Boolean{
        return emp.isLoggedIn==true
    }


    fun minu3(){
        println("\t1-Show Employee List ")
        println("\t2-Get specific Employee data ")
        println("\t3-Update data ")
        println("\t4-remove employee")
        println("\t5-Go Back")
        println("\t6-Exit")

    }
}


fun main(){
    val ob=Sysytem()
    while(true){
        println("\t\t\t\t\t\t\t\t\t\t\t\t  ______________________________")
        println("\t\t\t\t\t\t\t\t\t\t\t\t  Hello in system University")
        println("\t\t\t\t\t\t\t\t\t\t\t\t  ______________________________")
        println("\t\t\t\t\t\t\t\t1-Sign Up As Student\t\t\t\t\t\t\t\t2-Login as student"  )
        println("\t\t\t\t\t\t\t\t3-Sign Up As Instructor\t\t\t\t\t\t\t\t4-Login As Instructor ")
        println("\t\t\t\t\t\t\t\t5-Sign Up As Employee\t\t\t\t\t\t\t\t6-Login As Employee")
        println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t7-Exit")
        println("Enter Your Choise (1/7)")
        val choise = readln().toIntOrNull()?:return
        when(choise){
            1->{
                ob.addStudent()

                while (true){
                    ob.minu1()
                    println("Enter The Number Of operation You Want : ")
                    val num1= readln().toIntOrNull()?:return
                    when(num1){
                        1->ob.ListOfStudent()
                        2->ob.SearchStudentThenGet()
                        3->ob.UpdataStudentData()
                        4->{
                            ob.Display_Course()
                            ob.enrollCourse()
                        }
                        5->ob.Dispaly_Student_Enrolled()
                        6->ob.RemoveStudent()
                        7->break
                        8->return
                        else-> println("Invalid Value Try Again ..")
                    }
                }



            }
            2->{
                ob.LOgin()
                if(ob.Islogin()) {
                    while (true) {
                        ob.minu1()
                        println("Enter The Number Of operation You Want : ")
                        val num1 = readln().toIntOrNull() ?: return
                        when (num1) {
                            1 -> ob.ListOfStudent()
                            2 -> ob.SearchStudentThenGet()
                            3 -> ob.UpdataStudentData()
                            4 -> {
                                ob.Display_Course()
                                ob.enrollCourse()
                            }

                            5 -> ob.Dispaly_Student_Enrolled()
                            6 -> ob.RemoveStudent()
                            7 -> break
                            8 -> return
                            else -> println("Invalid Value Try Again ..")
                        }
                    }
                }else{
                    println("Login failed....Uncorrect Email")
                }
            }
            3->{
                ob.addInstructor()
                while (true){
                    ob.minu2()
                    println("Enter The Number Of operation You Want : ")
                    val num2= readln().toIntOrNull()?:return
                    when(num2){
                        1->ob.ListOfInstructors()
                        2->ob.searchInstructorThenGet()
                        3->ob.UpdateInstructorData()
                        4->ob.Display_Course()
                        5->ob.RemoveInstructor()
                        6->break
                        7->return
                        else-> println("Invalid Value Try Again ..")
                    }
                }
            }
            4->{
                ob.LOgin2()
                if(ob.Islogin2()){
                    while (true){
                        ob.minu2()
                        println("Enter The Number Of operation You Want : ")
                        val num2= readln().toIntOrNull()?:return
                        when(num2){
                            1->ob.ListOfInstructors()
                            2->ob.searchInstructorThenGet()
                            3->ob.UpdateInstructorData()
                            4->ob.Display_Course()
                            5->ob.RemoveInstructor()
                            6->break
                            7->return
                            else-> println("Invalid Value Try Again ..")
                        }
                    }
                }else{
                    println("Login failed....Uncorrect Email")
                }
            }
            5->{
                ob.addEmployee()
                while (true){
                    ob.minu3()
                    println("Enter The Number Of operation You Want : ")
                    val num3= readln().toIntOrNull()?:return
                    when(num3){
                        1->ob.ListOfEmp()
                        2->ob.searchEmployeeThenGet()
                        3->ob.UpdateEmployeeData()
                        4->ob.RemoveEmployee()
                        5->break
                        6->return
                        else-> println("Invalid Value Try Again ..")
                    }
                }
            }
            6->{
                ob.LOgin3()
                if(ob.Islogin3()){
                    while (true){
                        ob.minu3()
                        println("Enter The Number Of operation You Want : ")
                        val num3= readln().toIntOrNull()?:return
                        when(num3){
                            1->ob.ListOfEmp()
                            2->ob.searchEmployeeThenGet()
                            3->ob.UpdateEmployeeData()
                            4->ob.RemoveEmployee()
                            5->break
                            6->return
                            else-> println("Invalid Value Try Again ..")
                        }
                    }
                }else{
                    println("Login failed....Uncorrect Email")
                }
            }
            7->{
                return
            }
            else-> println("Invalid Value Try Again ..")
        }

    }
}
