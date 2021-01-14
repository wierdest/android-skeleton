package me.wierdest.myapplication;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.wierdest.myapplication.databinding.ActivityMainBindingImpl;
import me.wierdest.myapplication.databinding.FragmentHomeBindingImpl;
import me.wierdest.myapplication.databinding.FragmentSettingsBindingImpl;
import me.wierdest.myapplication.databinding.FragmentTabSnatcherBindingImpl;
import me.wierdest.myapplication.databinding.FragmentTitleBindingImpl;
import me.wierdest.myapplication.databinding.ListItemTabBindingImpl;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYMAIN = 1;

  private static final int LAYOUT_FRAGMENTHOME = 2;

  private static final int LAYOUT_FRAGMENTSETTINGS = 3;

  private static final int LAYOUT_FRAGMENTTABSNATCHER = 4;

  private static final int LAYOUT_FRAGMENTTITLE = 5;

  private static final int LAYOUT_LISTITEMTAB = 6;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(6);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(me.wierdest.myapplication.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(me.wierdest.myapplication.R.layout.fragment_home, LAYOUT_FRAGMENTHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(me.wierdest.myapplication.R.layout.fragment_settings, LAYOUT_FRAGMENTSETTINGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(me.wierdest.myapplication.R.layout.fragment_tab_snatcher, LAYOUT_FRAGMENTTABSNATCHER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(me.wierdest.myapplication.R.layout.fragment_title, LAYOUT_FRAGMENTTITLE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(me.wierdest.myapplication.R.layout.list_item_tab, LAYOUT_LISTITEMTAB);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTHOME: {
          if ("layout/fragment_home_0".equals(tag)) {
            return new FragmentHomeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_home is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTSETTINGS: {
          if ("layout/fragment_settings_0".equals(tag)) {
            return new FragmentSettingsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_settings is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTTABSNATCHER: {
          if ("layout/fragment_tab_snatcher_0".equals(tag)) {
            return new FragmentTabSnatcherBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_tab_snatcher is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTTITLE: {
          if ("layout/fragment_title_0".equals(tag)) {
            return new FragmentTitleBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_title is invalid. Received: " + tag);
        }
        case  LAYOUT_LISTITEMTAB: {
          if ("layout/list_item_tab_0".equals(tag)) {
            return new ListItemTabBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for list_item_tab is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(3);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "item");
      sKeys.put(2, "myViewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(6);

    static {
      sKeys.put("layout/activity_main_0", me.wierdest.myapplication.R.layout.activity_main);
      sKeys.put("layout/fragment_home_0", me.wierdest.myapplication.R.layout.fragment_home);
      sKeys.put("layout/fragment_settings_0", me.wierdest.myapplication.R.layout.fragment_settings);
      sKeys.put("layout/fragment_tab_snatcher_0", me.wierdest.myapplication.R.layout.fragment_tab_snatcher);
      sKeys.put("layout/fragment_title_0", me.wierdest.myapplication.R.layout.fragment_title);
      sKeys.put("layout/list_item_tab_0", me.wierdest.myapplication.R.layout.list_item_tab);
    }
  }
}
