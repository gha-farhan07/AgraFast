package com.agrafast.ui.screen.authetication.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.agrafast.ui.component.auththication.CircularLoading
import com.agrafast.ui.component.auththication.GoogleComponent
import com.agrafast.ui.component.auththication.TextLoginWith
import com.agrafast.ui.navigation.Screen
import com.agrafast.ui.screen.authetication.AuthViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel?,
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginFlow = authViewModel?.loginFlow?.collectAsState()


        Column() {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .shadow(6.dp),
                contentAlignment = Alignment.CenterStart
            ) {

            }
            //Banner Things
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .shadow(6.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Image(
                    painter = painterResource(id = R.drawable.authetication_banner),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .sizeIn(maxHeight = 180.dp, minWidth = 360.dp)
                        .fillMaxWidth()
                )
                Column {
                    Text(
                        text = stringResource(id = R.string.image_auth_banner).uppercase(),
                        color = Color.White,
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.padding(start = 16.dp),

                        )
                }
            }

            //Text dibawah Banner
            Column {
                Text(
                    text = "Login to start with", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                )
                Text(
                    text = "Ultimate Farming App", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Email TextField

            OutlinedTextField(
                value = email, onValueChange = { email = it },
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

            //Password TextField

            var passwordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = password,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .padding(start = 12.dp, end = 12.dp),
                onValueChange = { password = it },
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

            // Button TextField
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(minHeight = 100.dp)
            ) {
                Button(
                    onClick = { authViewModel?.loginUser(email, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp)
                        .align(Alignment.Center),
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.Button)
                    )
                ) {
                    Text("Login")
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 12.dp)
                ) {
                    Text(
                        "Forget Password ?",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Right,
                        color = colorResource(id = R.color.Button),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            loginFlow?.value?.let {
                when (it) {
                    is FetchStatus.Error -> {
                        val context = LocalContext.current
                        Toast.makeText(context, it.Exception.message, Toast.LENGTH_SHORT).show()
                    }

                    FetchStatus.Loading -> {
                        CircularLoading()
                    }

                    is FetchStatus.Success -> {
                        LaunchedEffect(Unit) {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        }
                    }
                }
            }


            //Divider and Annotated Text
            TextLoginWith(changeTextColor = false, useDivider = true)
            Spacer(modifier = Modifier.height(16.dp))
            GoogleComponent()
            TextLoginWith(
                "Doesn`t have an account?  Register", changeTextColor = true,
                colorText = "Register", plainText = "Doesn`t have an account?",
                onRegisterClick = { navController.navigate(Screen.Register.route) })
        }


    }


//    AgraFastTheme {
//        // A surface container using the 'background' color from the theme
//        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//           Column() {
//               BannerAuth()
//               BottomShadow()
//               HeadlineText()
//               Spacer(modifier = Modifier.height(16.dp))
//               TextField("Email", R.drawable.ic_profile)
//               Spacer(modifier = Modifier.height(8.dp))
//               PasswordTextField()
//               ButtonAuth(navController = navController)
//               TextLoginWith(changeTextColor = false, useDivider = true)
//               Spacer(modifier = Modifier.height(16.dp))
//               GoogleComponent()
//               TextLoginWith(
//                   "Doesn`t have an account?  Register", changeTextColor = true,
//                   colorText = "Register", plainText = "Doesn`t have an account?",
//                   onRegisterClick = {navController.navigate(Screen.Register.route)}
//               )
//           }
//        }}


@Preview
@Composable
fun LoginScreenPreview() {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        LoginScreen(navController = rememberNavController(), authViewModel = null)
    }
}




