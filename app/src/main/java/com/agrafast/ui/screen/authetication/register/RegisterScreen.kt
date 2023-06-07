package com.agrafast.ui.screen.authetication.register

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.agrafast.R
import com.agrafast.domain.FetchStatus
import com.agrafast.ui.component.auththication.AnimationLoading
import com.agrafast.ui.component.auththication.BannerAuth
import com.agrafast.ui.component.auththication.ButtonAuth
import com.agrafast.ui.component.auththication.CircularLoading
import com.agrafast.ui.component.auththication.GoogleComponent
import com.agrafast.ui.component.auththication.HeadlineText
import com.agrafast.ui.component.auththication.PasswordTextField
import com.agrafast.ui.component.auththication.TextField
import com.agrafast.ui.component.auththication.TextLoginWith
import com.agrafast.ui.navigation.Screen
import com.agrafast.ui.screen.authetication.AuthViewModel
import com.agrafast.ui.theme.AgraFastTheme

@Composable
fun RegisterScreen(navController: NavController, authViewModel: AuthViewModel?) {
    var emailRegister by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var telp by remember { mutableStateOf("") }
    val signupFlow = authViewModel?.signupFlow?.collectAsState()

    Column() {
        BannerAuth()
        HeadlineText(
            text1 = "Signup to start with",
            text2 = "Ultimate Farming App"
        )
        Spacer(modifier = Modifier.height(16.dp))

        //Textfield Nama
        OutlinedTextField(
            value = nama, onValueChange = { nama = it },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .padding(start = 12.dp, end = 12.dp),
            label = { Text(text = "Name") },
            keyboardOptions = KeyboardOptions.Default,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_profile),
                    contentDescription = ""
                )
            },
        )

        //Email TextField
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = emailRegister, onValueChange = { emailRegister = it },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .padding(start = 12.dp, end = 12.dp),
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions.Default,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_profile),
                    contentDescription = ""
                )
            },
        )


        //Telp TextField
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = telp, onValueChange = { telp = it },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .padding(start = 12.dp, end = 12.dp),
            label = { Text(text = "Telp. Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_phone),
                    contentDescription = ""
                )
            },
        )

        Spacer(modifier = Modifier.height(8.dp))
        //Password TextField
        var passwordRegister by rememberSaveable { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = passwordRegister,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .padding(start = 12.dp, end = 12.dp),
            onValueChange = { passwordRegister = it },
            label = { Text("Enter password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_password),
                    contentDescription = ""
                )

            },
            trailingIcon = {
                val iconImage = if (passwordVisible) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                }

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = iconImage, contentDescription = null)
                }
            },
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Register Button
        Button(
            onClick = { authViewModel?.signupUser(nama, emailRegister, telp, passwordRegister) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.Button),
                contentColor = Color.White
            )
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Signup Flow
        signupFlow?.value?.let {
            when (it) {
                is FetchStatus.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }
                    }
                }
                is FetchStatus.Error -> {
                    val context = LocalContext.current
                    Toast.makeText(context, it.Exception.message, Toast.LENGTH_SHORT).show()
                }
                is FetchStatus.Loading -> {
                    CircularLoading()
                }
                else -> TODO()
            }
        }



        GoogleComponent(
            text = "Signup in with Google", loadingText = "Create Account..."
        )
        TextLoginWith(
            text = "Already have an account ? Login",
            changeTextColor = true,
            colorText = "Login",
            plainText = "Already have an account ? ",
            onRegisterClick = { navController.navigate(Screen.Login.route) }
        )







    }
//    AgraFastTheme {
//        // A surface container using the 'background' color from the theme
//        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//            Column() {
//                BannerAuth()
//                HeadlineText(text1 = "Signup to start with",
//                    text2 = "Ultimate Farming App")
//                Spacer(modifier = Modifier.height(16.dp))
//                TextField(labelValue = "Nama")
//                Spacer(modifier = Modifier.height(8.dp))
//                TextField(labelValue = "Email")
//                Spacer(modifier = Modifier.height(8.dp))
//                TextField(labelValue = "No.Telp", icon = R.drawable.ic_phone)
//                Spacer(modifier = Modifier.height(8.dp))
//                PasswordTextField()
//                ButtonAuth(text = "Register", false, navController = navController, loginPage = false)
//                GoogleComponent(
//                    text= "Signup in with Google", loadingText = "Create Account..."
//                )
//                TextLoginWith(
//                    text= "Already have an account ? Login", changeTextColor = true,
//                    colorText = "Login", plainText = "Already have an account ? ", onRegisterClick = {navController.navigate(Screen.Login.route)}
//                )
//            }
//        }
//    }
}


@Preview
@Composable
fun RegisterScreenPreview() {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        RegisterScreen(navController = rememberNavController(), authViewModel = null)
    }
}