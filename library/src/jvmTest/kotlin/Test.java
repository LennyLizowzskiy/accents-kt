import com.lizowzskiy.accents.AccentUtil;
import com.lizowzskiy.accents.Color;

class Foo {
    public void bar() {
        Color accent = AccentUtil.getAccentColor();
        System.out.println(accent);
    }
}