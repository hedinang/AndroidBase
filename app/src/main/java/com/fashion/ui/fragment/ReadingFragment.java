package com.fashion.ui.fragment;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.fashion.R;
import com.fashion.core.ui.BaseFragment;
import com.fashion.data.Chapter;
import com.fashion.data.model.Book;
import com.fashion.ui.adapter.PageBookAdapter;
import com.fashion.ui.view_model.ChapterBookViewModel;
import com.fashion.ui.view_model.SettingViewModel;
import com.fashion.utils.ViewUtils;
import com.fashion.widget.page_curl.CurlView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Optional;

@SuppressLint("NonConstantResourceId")
public class ReadingFragment extends BaseFragment {
    public static int MAX_LINE;
    public static int MAX_LINE_FIRST_PAGE;
    public static int LENGTH_WHITE_SPACE;
    public static String LINE_BLACK_SPACE;
    public static String LINE_WHITE_SPACE;
    public static float WIDTH_MATCH_TEXT;
    public static float HEIGHT_MATCH_TEXT;
    public static float HEIGHT_MATCH_TEXT_FIRST_PAGE;
    Book book;

    public static ReadingFragment newInstance(Book book) {
        ReadingFragment fragment = new ReadingFragment();
        fragment.book = book;
        return fragment;
    }

    ChapterBookViewModel chapterBookViewModel;
    SettingViewModel settingViewModel;
    @BindView(R.id.cv_pages)
    CurlView cvPage;
    @BindView(R.id.tv_Test)
    TextView tvTest;
    @BindView(R.id.ll_menu_setting_text)
    LinearLayout llMenuSettingText;
    PageBookAdapter pageBookAdapter;
    @BindView(R.id.sb_brightness)
    SeekBar seekBarBrightness;

    @Override
    protected int getLayout() {
        return R.layout.fragment_reading;
    }

    @Override
    protected void initStateUI() {
        super.initStateUI();
        float width = Resources.getSystem().getDisplayMetrics().widthPixels;
        float height = Resources.getSystem().getDisplayMetrics().heightPixels;
        WIDTH_MATCH_TEXT = width - ViewUtils.dpToPx(this.mContext, 40);
        HEIGHT_MATCH_TEXT = height - ViewUtils.dpToPx(this.mContext, 112);
        HEIGHT_MATCH_TEXT_FIRST_PAGE = height - ViewUtils.dpToPx(this.mContext, 112);
        initConfigText();
        initPageBook();
        chapterBookViewModel.chapters.observe(this, chapters -> {
            int currentPage = cvPage.getCurrentIndex();
            cvPage.setVisibility(View.VISIBLE);
            pageBookAdapter.addMore(handleInputPageBook(chapters));
            cvPage.setCurrentIndex(currentPage);
            cvPage.updatePages();
            cvPage.requestRender();
        });
    }

    private void initConfigText() {
        MAX_LINE = (int) (HEIGHT_MATCH_TEXT / tvTest.getLineHeight()) - 1;

        MAX_LINE_FIRST_PAGE = MAX_LINE - 2;

        LENGTH_WHITE_SPACE = tvTest.getPaint().breakText(
                "                                                                        ",
                true, WIDTH_MATCH_TEXT, null);
        StringBuilder builderBlackSpace = new StringBuilder();
        StringBuilder builderWhiteSpace = new StringBuilder();
        for (int i = 0; i <= LENGTH_WHITE_SPACE; i++) {

            builderBlackSpace.append("-");
            builderWhiteSpace.append(" ");

        }
        LINE_BLACK_SPACE = builderBlackSpace.append(" ").toString();
        LINE_WHITE_SPACE = builderWhiteSpace.append(" ").toString();

    }

    private void initPageBook() {
        int index = 0;
        pageBookAdapter = new PageBookAdapter(mContext, new ArrayList<>());
        pageBookAdapter.setPageListener(() -> {
            chapterBookViewModel.loadChapters(book.getId());
        });
        cvPage.setPageProvider(pageBookAdapter);
        cvPage.setSizeChangedObserver(new SizeChangedObserver());
        cvPage.setCurrentIndex(index);
        cvPage.setAllowLastPageCurl(false);
        cvPage.setEnableTouchPressure(false);
        cvPage.setVisibility(View.GONE);
    }

    private List<PageBookAdapter.PageChapter> handleInputPageBook(List<Chapter> chapters) {
        Collections.sort(chapters, (chapter, chapter2) -> chapter.chapterId - chapter2.chapterId);
        List<PageBookAdapter.PageChapter> result = new ArrayList<>();
        String SPACE = " ";
        for (Chapter chapter : chapters) {
            String item = chapter.chapterContent;
            item = item.replace("\\n", LINE_BLACK_SPACE);
            String[] words = item.split("\\s+");
            int line = 0;
            int currentPosition = 0;
            boolean isChangeLine = true;
            int maxCharOfLine = 0;
            StringBuilder builder = new StringBuilder();
            int counter = words.length;
            boolean isFirstPage = true;
            for (int i = 0; i < counter; i++) {
                int max_line = MAX_LINE;
                if (isFirstPage)
                    max_line = MAX_LINE_FIRST_PAGE;
                if (isChangeLine) {
                    isChangeLine = false;
                    int startPosition = builder.length();
                    int endPosition = Math.min(startPosition + 100, item.length());
                    maxCharOfLine = tvTest.getPaint().breakText(item.substring(startPosition, endPosition),
                            true, WIDTH_MATCH_TEXT, null);
                }
                builder.append(words[i]);
                builder.append(SPACE);
                int lengthOfNextWord = 0;
                if (i < counter - 1) {
                    lengthOfNextWord = words[i + 1].length();
                }
                int tmp = (builder.length() + lengthOfNextWord) - currentPosition;
                if (tmp > maxCharOfLine) {
                    isChangeLine = true;
                    line++;
                    currentPosition = builder.toString().length();
                }
                if (i == counter - 1 && line < max_line) {
                    PageBookAdapter.PageChapter pageChapter = new PageBookAdapter.PageChapter();
                    pageChapter.isFirst = isFirstPage;
                    pageChapter.chapterTitle = chapter.chapterTitle;
                    pageChapter.content = builder.toString();
                    result.add(pageChapter);
                }
                /*break page*/
                if (line == max_line) {
                    isChangeLine = true;
                    PageBookAdapter.PageChapter pageChapter = new PageBookAdapter.PageChapter();
                    pageChapter.isFirst = isFirstPage;
                    pageChapter.chapterTitle = chapter.chapterTitle;
                    pageChapter.content = builder.toString();
                    result.add(pageChapter);
                    line = 0;
                    currentPosition = 0;
                    isFirstPage = false;
                    builder = new StringBuilder();
                }
            }
        }

        return result;
    }

    private class SizeChangedObserver implements CurlView.SizeChangedObserver {
        @Override
        public void onSizeChanged(int w, int h) {
            if (w > h) {
                cvPage.setViewMode(CurlView.SHOW_TWO_PAGES);
            } else {
                cvPage.setViewMode(CurlView.SHOW_ONE_PAGE);
            }
            cvPage.setMargins(0f, 0f, 0f, 0f);
        }
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        fragmentComponent.inject(this);
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        chapterBookViewModel = new ViewModelProvider(this, viewModelProvider).get(ChapterBookViewModel.class);
        settingViewModel = new ViewModelProvider(this, viewModelProvider).get(SettingViewModel.class);
    }

    @Override
    protected void loadData() {
        super.loadData();
        chapterBookViewModel.loadChapters(book.getId());
        settingViewModel.loadSetting();
        initBrightness();
    }

    @Override
    protected void setupUI(View view) {

    }

    @OnClick(R.id.btn_back)
    void onBack() {
        onBackPressed();
    }

    @OnClick(R.id.fr_root)
    void doNot() {
    }

    @OnClick(R.id.ll_menu_setting_text)
    void onClickMenu() {
    }

    @Optional
    @OnClick({
            R.id.btn_white,
            R.id.btn_yellow,
            R.id.btn_black,
    })
    void onSettingBackgroundItem(View view){
        int id = view.getId();
        switch (id){
            case R.id.btn_white:
                pageBookAdapter.setBackgroundItem("#FFFFFF");
                pageBookAdapter.setTextColor("#000000");
                break;
            case R.id.btn_yellow:
                pageBookAdapter.setBackgroundItem("#F6E5C4");
                pageBookAdapter.setTextColor("#000000");
                break;
            case R.id.btn_black:
                pageBookAdapter.setBackgroundItem("#000000");
                pageBookAdapter.setTextColor("#FFFFFF");
                break;
        }

        cvPage.updatePages();
        cvPage.requestRender();
    }

    @OnCheckedChanged(R.id.cb_setting_text)
    void onCbSettingTextChanged(CompoundButton button, boolean checked) {
        if (checked) {
            llMenuSettingText.setVisibility(View.VISIBLE);
        } else {
            llMenuSettingText.setVisibility(View.GONE);
        }
    }

    private void initBrightness() {
        ContentResolver contentResolver = mActivity.getContentResolver();
        Window window = mActivity.getWindow();
        seekBarBrightness.setMax(100);
        seekBarBrightness.setKeyProgressIncrement(1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.System.canWrite(this.mContext);
        }

        try {
            if (settingViewModel.brightnessReadScreen == null || settingViewModel.brightnessReadScreen == 0f) {
                settingViewModel.brightnessReadScreen = (Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS) * 100) / 255.0f;
            }
            seekBarBrightness.setProgress(settingViewModel.brightnessReadScreen.intValue());
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.screenBrightness = settingViewModel.brightnessReadScreen / (float) 100;
            window.setAttributes(layoutParams);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        // seekbar brightness change listener
        seekBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.screenBrightness = settingViewModel.brightnessReadScreen / (float) 100;
                window.setAttributes(layoutParams);
                settingViewModel.saveSetting();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 1 && progress < 100) {
                    settingViewModel.brightnessReadScreen = (float) progress;
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        cvPage.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        cvPage.onResume();
    }

    @Override
    public void onDestroy() {
        try {
            ContentResolver contentResolver = mActivity.getContentResolver();
            Window window = mActivity.getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.screenBrightness = (Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS) * 100) / 255.0f;
            window.setAttributes(layoutParams);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        chapterBookViewModel.dispose();
        super.onDestroy();
    }


}
