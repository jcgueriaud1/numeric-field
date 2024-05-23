package com.example.application.views.numericfield;

import com.example.application.views.MainLayout;

import com.vaadin.componentfactory.addons.inputmask.InputMask;
import com.vaadin.componentfactory.addons.inputmask.InputMaskOption;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Numeric Field")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class NumericFieldView extends VerticalLayout {
    private Binder<DataBean> dataBeanBinder = new Binder<>();

    private VerticalLayout dataContainer = new VerticalLayout();
    public NumericFieldView() {

        TextField numberField = new TextField("Number in a text field formatted as 11 111.11");
        InputMask inputMask = new InputMask("Number", true, InputMaskOption.option("scale", 2),
                InputMaskOption.option("thousandsSeparator", "."), InputMaskOption.option("radix", ','));

        inputMask.extend(numberField);

        CustomBigDecimalField bigDecimalField = new CustomBigDecimalField("Number as bigdecimal");

        dataBeanBinder.forField(numberField).bind(DataBean::getNumberAsString, DataBean::setNumberAsString);
        dataBeanBinder.forField(bigDecimalField).bind(DataBean::getNumberAsBigDecimal, DataBean::setNumberAsBigDecimal);

        dataBeanBinder.setBean(new DataBean());

        dataBeanBinder.addValueChangeListener(e -> {
            dataContainer.removeAll();
            dataContainer.add(new Span("numberAsString:" + dataBeanBinder.getBean().getNumberAsString()));
            dataContainer.add(new Span("numberAsBigDecimal:" + dataBeanBinder.getBean().getNumberAsBigDecimal()));
            dataContainer.add(new Span("Formatted numberAsBigDecimal:" + CustomBigDecimalField.getNumberFormat().format(dataBeanBinder.getBean().getNumberAsBigDecimal())));
        });

        add(numberField, bigDecimalField, dataContainer);
    }

}
