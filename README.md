# Current issue

App opens with splash screen. Splash screen will try to call the api 
which isn't there. Expected behaviour is the viewmodel state updates the result 
to QueryState.Failure with a cause. I was hoping the data binding between the splash_fragment.xml
and the MainViewModel would mean that when the state changes, the progress bar visibility is set to "GONE"
and the error text field visibility is set to "VISIBLE" (using the BindingAdapter class). I could update the
view from the SplashFragment class but I'm trying to make bindings work so that would seem to defeat the purpose.

It appears that although the viewmodel state has changed, the view is not updated? Not sure what's happening here.