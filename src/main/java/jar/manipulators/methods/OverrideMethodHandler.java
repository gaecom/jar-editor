package jar.manipulators.methods;

import controllers.Controller;
import jar.utils.ButtonEventHandler;
import jar.utils.ClassResolver;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.util.List;

public class OverrideMethodHandler extends ButtonEventHandler {


    public static ButtonEventHandler getDefault(TreeView<String> treeView, TextArea textArea, ListView<String> listView) {
        ButtonEventHandler buttonEventHandler = new jar.manipulators.methods.OverrideMethodHandler("Override method", treeView, textArea, listView);
        return buttonEventHandler;
    }


    public OverrideMethodHandler(String name, TreeView<String> treeView, TextArea textArea, ListView<String> listView) {
        super(name, treeView, textArea, listView);
    }

    /**
     * Override selected method body
     * @throws NotFoundException
     * @throws CannotCompileException
     */
    @Override
    public void manipulate() throws NotFoundException, CannotCompileException {

        String classpath = resolvePath();
        if (!classpath.isEmpty()) {
            if (classpath.contains(".class")) {
                if (!listView.getSelectionModel().isEmpty()) {
                    String name = listView.getSelectionModel().getSelectedItem();
                    CtClass ctClass = ClassResolver.resolveClass(classpath);
                    CtMethod methodToOverride = ctClass.getDeclaredMethod(name);
                    if(!textArea.getText().isEmpty()) {
                        methodToOverride.setBody(textArea.getText());
                    }
                }
                else
                    Controller.showInfo("Choose method from list");
            }
        }

    }


    /**
     *
     * @return list of methods in class object
     * @throws NotFoundException
     */
    @Override
    public List<String> fillList() throws NotFoundException {
        return ListMethods.fillList(resolvePath());
    }
}
