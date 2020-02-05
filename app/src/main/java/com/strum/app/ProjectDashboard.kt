package com.strum.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_project_dashboard.*

class ProjectDashboard : AppCompatActivity() {

    var tabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_dashboard)

        tabLayout = findViewById(R.id.tasksTabs)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("My Tasks"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("All Tasks"))

        //get team memb list form db


        // add only 3 elements
        var teamList = ArrayList<TeamMemberModel>()

        teamList.add(TeamMemberModel("https://www.google.com/imgres?imgurl=https%3A%2F%2Flookaside.fbsbx.com%2Flookaside%2Fcrawler%2Fmedia%2F%3Fmedia_id%3D1782996298392336&imgrefurl=https%3A%2F%2Fwww.facebook.com%2FMia-Khalifa-fans-1782996298392336%2F&tbnid=6a1G6QUobCnHRM&vet=12ahUKEwjxxsfavrrnAhVHf30KHZAUAMoQMygBegUIARDAAQ..i&docid=bY6Z5N2Ct2ufgM&w=566&h=600&q=mia%20khalifa%20profile%20pic&ved=2ahUKEwjxxsfavrrnAhVHf30KHZAUAMoQMygBegUIARDAAQ"))
        teamList.add(TeamMemberModel("https://www.google.com/imgres?imgurl=https%3A%2F%2Fstatic.independent.co.uk%2Fs3fs-public%2Fthumbnails%2Fimage%2F2020%2F01%2F23%2F15%2FPutin.jpg%3Fw968h681&imgrefurl=https%3A%2F%2Fwww.independent.co.uk%2Fnews%2Fworld%2Feurope%2Frussia-putin-government-parliament-constitution-votes-a9299071.html&tbnid=Prftdtsw26DSUM&vet=12ahUKEwibh66tvrrnAhX6kksFHbJ5CVMQMygGegUIARCWAg..i&docid=j_dfqknIFzyPqM&w=968&h=681&q=putin&ved=2ahUKEwibh66tvrrnAhX6kksFHbJ5CVMQMygGegUIARCWAg"))
        teamList.add(TeamMemberModel("https://www.google.com/imgres?imgurl=https%3A%2F%2Flookaside.fbsbx.com%2Flookaside%2Fcrawler%2Fmedia%2F%3Fmedia_id%3D1782996298392336&imgrefurl=https%3A%2F%2Fwww.facebook.com%2FMia-Khalifa-fans-1782996298392336%2F&tbnid=6a1G6QUobCnHRM&vet=12ahUKEwjxxsfavrrnAhVHf30KHZAUAMoQMygBegUIARDAAQ..i&docid=bY6Z5N2Ct2ufgM&w=566&h=600&q=mia%20khalifa%20profile%20pic&ved=2ahUKEwjxxsfavrrnAhVHf30KHZAUAMoQMygBegUIARDAAQ"))
        teamList.add(TeamMemberModel("https://www.google.com/imgres?imgurl=https%3A%2F%2Fstatic.independent.co.uk%2Fs3fs-public%2Fthumbnails%2Fimage%2F2020%2F01%2F23%2F15%2FPutin.jpg%3Fw968h681&imgrefurl=https%3A%2F%2Fwww.independent.co.uk%2Fnews%2Fworld%2Feurope%2Frussia-putin-government-parliament-constitution-votes-a9299071.html&tbnid=Prftdtsw26DSUM&vet=12ahUKEwibh66tvrrnAhX6kksFHbJ5CVMQMygGegUIARCWAg..i&docid=j_dfqknIFzyPqM&w=968&h=681&q=putin&ved=2ahUKEwibh66tvrrnAhX6kksFHbJ5CVMQMygGegUIARCWAg"))
        teamList.add(TeamMemberModel("https://www.google.com/imgres?imgurl=https%3A%2F%2Flookaside.fbsbx.com%2Flookaside%2Fcrawler%2Fmedia%2F%3Fmedia_id%3D1782996298392336&imgrefurl=https%3A%2F%2Fwww.facebook.com%2FMia-Khalifa-fans-1782996298392336%2F&tbnid=6a1G6QUobCnHRM&vet=12ahUKEwjxxsfavrrnAhVHf30KHZAUAMoQMygBegUIARDAAQ..i&docid=bY6Z5N2Ct2ufgM&w=566&h=600&q=mia%20khalifa%20profile%20pic&ved=2ahUKEwjxxsfavrrnAhVHf30KHZAUAMoQMygBegUIARDAAQ"))
        teamList.add(TeamMemberModel("https://www.google.com/imgres?imgurl=https%3A%2F%2Fstatic.independent.co.uk%2Fs3fs-public%2Fthumbnails%2Fimage%2F2020%2F01%2F23%2F15%2FPutin.jpg%3Fw968h681&imgrefurl=https%3A%2F%2Fwww.independent.co.uk%2Fnews%2Fworld%2Feurope%2Frussia-putin-government-parliament-constitution-votes-a9299071.html&tbnid=Prftdtsw26DSUM&vet=12ahUKEwibh66tvrrnAhX6kksFHbJ5CVMQMygGegUIARCWAg..i&docid=j_dfqknIFzyPqM&w=968&h=681&q=putin&ved=2ahUKEwibh66tvrrnAhX6kksFHbJ5CVMQMygGegUIARCWAg"))
        teamList.add(TeamMemberModel("https://www.google.com/imgres?imgurl=https%3A%2F%2Flookaside.fbsbx.com%2Flookaside%2Fcrawler%2Fmedia%2F%3Fmedia_id%3D1782996298392336&imgrefurl=https%3A%2F%2Fwww.facebook.com%2FMia-Khalifa-fans-1782996298392336%2F&tbnid=6a1G6QUobCnHRM&vet=12ahUKEwjxxsfavrrnAhVHf30KHZAUAMoQMygBegUIARDAAQ..i&docid=bY6Z5N2Ct2ufgM&w=566&h=600&q=mia%20khalifa%20profile%20pic&ved=2ahUKEwjxxsfavrrnAhVHf30KHZAUAMoQMygBegUIARDAAQ"))
        teamList.add(TeamMemberModel("https://www.google.com/imgres?imgurl=https%3A%2F%2Fstatic.independent.co.uk%2Fs3fs-public%2Fthumbnails%2Fimage%2F2020%2F01%2F23%2F15%2FPutin.jpg%3Fw968h681&imgrefurl=https%3A%2F%2Fwww.independent.co.uk%2Fnews%2Fworld%2Feurope%2Frussia-putin-government-parliament-constitution-votes-a9299071.html&tbnid=Prftdtsw26DSUM&vet=12ahUKEwibh66tvrrnAhX6kksFHbJ5CVMQMygGegUIARCWAg..i&docid=j_dfqknIFzyPqM&w=968&h=681&q=putin&ved=2ahUKEwibh66tvrrnAhX6kksFHbJ5CVMQMygGegUIARCWAg"))

        var teamListShort = ArrayList<TeamMemberModel>()
        while (teamListShort.size < 3){
            var randomPerson = teamList.random()
            if(!teamListShort.contains(randomPerson)){
                teamListShort.add(randomPerson)
            }
        }
        teamListShort.add(TeamMemberModel("END"))

        var adapter = TeamAdapter(applicationContext, teamListShort, teamList.size)

        teammembrv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}
