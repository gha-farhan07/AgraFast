package com.agrafast

import android.app.Activity.RESULT_OK
import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Identity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.agrafast.ui.navigation.NavItem
import com.agrafast.ui.navigation.Screen
import com.agrafast.ui.screen.GlobalViewModel
import com.agrafast.ui.screen.authetication.AuthViewModel
import com.agrafast.ui.screen.authetication.GoogleAuthClient
import com.agrafast.ui.screen.authetication.GoogleAuthViewModel
import com.agrafast.ui.screen.authetication.login.LoginScreen
import com.agrafast.ui.screen.authetication.register.RegisterScreen
import com.agrafast.ui.screen.detail.PlantDetailScreen
import com.agrafast.ui.screen.detector.DetectorScreen
import com.agrafast.ui.screen.home.HomeScreen
import com.agrafast.ui.screen.plant.PlantListScreen
import com.agrafast.ui.screen.profil.ProfileScreen
import com.agrafast.ui.screen.usersplant.UsersPlantsScreen
import com.agrafast.ui.theme.AgraFastTheme
import kotlinx.coroutines.DelicateCoroutinesApi


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AgraFastApp(
  navController: NavHostController = rememberNavController(),
  authViewModel: AuthViewModel,
  context: Context = LocalContext.current
) {
//  val googleAuthUiClient by lazy {
//    GoogleAuthClient(
//      context = context,
//      oneTapClient = com.google.android.gms.auth.api.identity.Identity.getSignInClient(context)
//    )
//  }
//
//
//  val googleAuth: GoogleAuthViewModel = hiltViewModel()
//  val state by googleAuth.state.collectAsState()
//  val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult(),
//    onResult = {result ->
//      if (result.resultCode == RESULT_OK) {
//        val signInResult = googleAuthUiClient.signInW()
//      }
//    })


  val navItems = listOf(
    NavItem(stringResource(R.string.home), Icons.Default.Home, Screen.Home),
    NavItem(
      stringResource(R.string.user_plant),
      ImageVector.vectorResource(id = R.drawable.ic_plant),
      Screen.UserPlant,
    ),
    NavItem(stringResource(R.string.profile), Icons.Default.Person, Screen.Profil),
  )

  var startDestination: String? = null

  if (authViewModel.currentUser?.uid != null) {
    startDestination = Screen.Home.route
  } else {
    startDestination = Screen.Login.route
  }

  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route

  Scaffold(
    bottomBar = {
      val isVisible = navItems.map { it.screen.route }.contains(currentRoute)

      BottomBarComponent(navItems, isVisible, currentRoute) {
        navController.navigate(it)
      }
    }
  ) { innerPadding ->
    val viewModel: GlobalViewModel = hiltViewModel()
    NavHost(
      navController = navController,
      startDestination = startDestination, modifier = Modifier.padding(innerPadding)
    ) {

      composable(Screen.Login.route) {
        LoginScreen(navController = navController, authViewModel = authViewModel)
      }
      composable(Screen.Register.route) {
        RegisterScreen(navController, authViewModel)
      }
      composable(Screen.Home.route) {
        HomeScreen(navController, viewModel, authViewModel)
      }
      composable(Screen.PlantList.route) {
        PlantListScreen(navController, viewModel)
      }
      composable(Screen.UserPlant.route) {
        UsersPlantsScreen(navController, viewModel)
      }
      composable(Screen.Profil.route) {
        ProfileScreen()
      }
      composable(route = Screen.PlantDetail.route) {
        PlantDetailScreen(navController, viewModel)
      }
      composable(Screen.DiseaseDetector.route) {
        DetectorScreen(viewModel)
      }
    }
  }
}

@Composable
fun BottomBarComponent(
  navItems: List<NavItem>,
  isVisible: Boolean,
  currentRoute: String?,
  onClickNavItem: (String) -> Unit
) {
  AnimatedVisibility(
    visible = isVisible,
    enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }),
    exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight })
  ) {
    NavigationBar {
      navItems.forEach {
        NavigationBarItem(
          selected = currentRoute == it.screen.route,
          onClick = { onClickNavItem(it.screen.route) },
          label = {
            Text(text = it.title, fontWeight = FontWeight.SemiBold)
          },
          icon = {
            Icon(it.icon, contentDescription = "${it.title}_page")
          }
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  AgraFastTheme {
  }
}