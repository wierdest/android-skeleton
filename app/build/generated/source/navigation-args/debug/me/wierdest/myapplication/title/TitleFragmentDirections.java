package me.wierdest.myapplication.title;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import me.wierdest.myapplication.R;

public class TitleFragmentDirections {
  private TitleFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionTitleFragmentToHomeFragment() {
    return new ActionOnlyNavDirections(R.id.action_titleFragment_to_homeFragment);
  }
}
