package pcd.lab03.gui.chrono;

public class TestCounting {
	public static void main(String[] args) {
		Counter c = new Counter(0);
        new CounterGUI(c).display();
	}
}
