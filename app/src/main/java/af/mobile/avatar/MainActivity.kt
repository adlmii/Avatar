package af.mobile.avatar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import af.mobile.avatar.ui.theme.AvatarTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AvatarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(modifier = Modifier.padding(innerPadding)) // Pass innerPadding here
                }
            }
        }
    }
}

@Serializable
object Home

@Serializable
object Login

@Serializable
object Register

@Serializable
object Avatar

@Serializable
data class Profile(val name: String? = "", val address: String? = "", val email: String? = "", val phone: String? = "")

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(keRegister = {
                navController.navigate("register")
            }, keHome = {
                navController.navigate("home")
            })
        }
        composable("home") {
            ProfileScreen(keAvatar = {
                navController.navigate("avatar")
            }, modifier = modifier) // Pass innerPadding here
        }
        composable("avatar") {
            AvatarScreen()
        }
        composable("register") {
            RegisterScreen(keLogin = {
                navController.navigate("login")
            })
        }
    }
}

@Composable
fun ProfileScreen(keAvatar: (() -> Unit)? = {}, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Hello Aidil!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Name\t: Aidil\n" +
                        "Address\t: Bandung\n" +
                        "Email\t: william.henry.moody@gmail.com\n" +
                        "Phone\t: 08123456789",
                modifier = Modifier.padding(16.dp)
            )
        }

        Button(
            onClick = { keAvatar?.invoke() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Customize Avatar")
        }
    }
}

@Composable
fun RegisterScreen(keLogin: (() -> Unit)? = {}, modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Register",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Input Fields
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { keLogin?.invoke() }, modifier = Modifier.fillMaxWidth()) {
            Text("Register")
        }
    }
}

@Composable
fun LoginScreen(keRegister: (() -> Unit)? = {}, keHome: (() -> Unit)? = {}, modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var resetEnabled by remember { mutableStateOf(false) }
    if (username == "" || password == "") resetEnabled = false
    else resetEnabled = true

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            enabled = resetEnabled,
            onClick = { keHome?.invoke() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { keRegister?.invoke() }) {
            Text("Belum Punya Akun? Daftar")
        }
    }
}

@Composable
fun AvatarScreen(modifier: Modifier = Modifier) {
    var showBrow by remember { mutableStateOf(true) }
    var showEye by remember { mutableStateOf(true) }
    var showNose by remember { mutableStateOf(true) }
    var showMouth by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Customize Your Avatar",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(bottom = 32.dp)
                .align(Alignment.CenterHorizontally)
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(R.drawable.face_0004), contentDescription = "Face")

            if (showBrow) {
                Image(
                    painter = painterResource(R.drawable.face_0001),
                    contentDescription = "Brow",
                    modifier = Modifier.align(Alignment.Center).padding(bottom = 80.dp)
                )
            }
            if (showEye) {
                Image(painter = painterResource(R.drawable.face_0003),
                    contentDescription = "Eye",
                    modifier = Modifier.align(Alignment.Center))
            }
            if (showNose) {
                Image(painter = painterResource(R.drawable.face_0002),
                    contentDescription = "Nose",
                    modifier = Modifier.align(Alignment.Center).padding(top = 80.dp))
            }
            if (showMouth) {
                Image(painter = painterResource(R.drawable.face_0000),
                    contentDescription = "Mouth",
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 100.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = showBrow, onCheckedChange = { showBrow = it })
            Text("Brow")

            Checkbox(checked = showEye, onCheckedChange = { showEye = it })
            Text("Eye")

            Checkbox(checked = showNose, onCheckedChange = { showNose = it })
            Text("Nose")

            Checkbox(checked = showMouth, onCheckedChange = { showMouth = it })
            Text("Mouth")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AvatarTheme {
        Greeting("Android")
    }
}
