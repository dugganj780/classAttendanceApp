package org.wit.classattendanceapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.classattendanceapp.main.MainApp
import org.wit.classattendanceapp.databinding.ActivityLoginBinding
import org.wit.classattendanceapp.models.StudentModel
import timber.log.Timber.i

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var student = StudentModel(0,"","","")
    lateinit var app: MainApp

    //Creates Login Screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarLogin.title = title
        setSupportActionBar(binding.toolbarLogin)

        app = application as MainApp

        binding.btnLogin.setOnClickListener {
            if (binding.studentId.text.toString() != "") {
                student.studentID = binding.studentId.text.toString().toLong()
            }
            student.password = binding.password.text.toString()

            //Iterates through existing users for basic authentication
            val iterator = app.students.findAllUsers().listIterator()
            for(item in iterator){
                i("${item.studentID}")
                i("${item.password}")
                if(item.studentID == student.studentID && item.password==student.password) {
                    student = item
                    i("${student}")
                    val launcherIntent = Intent(this, ModuleListActivity::class.java)
                    launcherIntent.putExtra("student_logged_in", student)
                    startActivityForResult(launcherIntent, 0)

                }
                //If user not found, Snackbar informs user that credentials are incorrect
                else{
                    i("StudentID: ${student.studentID.javaClass} Password: ${student.password.javaClass}")
                    Snackbar.make(it,"Invalid User Credentials", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}
