package com.example.application.views.numericfield;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.componentfactory.addons.inputmask.InputMask;
import com.vaadin.componentfactory.addons.inputmask.InputMaskOption;
import com.vaadin.flow.component.AbstractCompositeField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasAriaLabel;
import com.vaadin.flow.component.HasLabel;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.shared.HasPrefix;
import com.vaadin.flow.component.shared.HasSuffix;
import com.vaadin.flow.component.shared.HasTooltip;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.HasValidator;
import com.vaadin.flow.data.binder.ValidationStatusChangeEvent;
import com.vaadin.flow.data.binder.ValidationStatusChangeListener;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;

public class CustomBigDecimalField extends AbstractCompositeField<TextField, CustomBigDecimalField, BigDecimal>
        implements HasPrefix, HasSuffix, HasStyle, HasValidation, HasSize, Focusable<CustomBigDecimalField>, HasLabel, HasTooltip, HasValidator<BigDecimal>, HasAriaLabel {

    private final int scale;
    public static final char GROUPING_SEPARATOR = '.';
    public static final char DECIMAL_SEPARATOR = ',';


    public static DecimalFormat getNumberFormat() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(DECIMAL_SEPARATOR);
        decimalFormatSymbols.setGroupingSeparator(GROUPING_SEPARATOR);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", decimalFormatSymbols);
        decimalFormat.setParseBigDecimal(true);
        return decimalFormat;
    }
    public CustomBigDecimalField() {
        this("");
    }

    public CustomBigDecimalField(String label) {
        this(label, 0);
    }

    private CustomBigDecimalField(String label, int scale) {
        super(null);
        this.scale = scale;
        extend();

        getContent().addThemeName("align-right");
        getContent().setLabel(label);
        getContent().addValueChangeListener(event -> {
            if (event.isFromClient()) {
                try {
                    setModelValue(StringUtils.isNotEmpty(event.getValue()) ? (BigDecimal) getNumberFormat().parse(event.getValue()) : null, event.isFromClient());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void extend() {
            new InputMask("Number", true,
                    InputMaskOption.option("scale", scale),
                    InputMaskOption.option("thousandsSeparator", "."),
                    InputMaskOption.option("radix", DECIMAL_SEPARATOR),
                    InputMaskOption.option("min", 0))
                    .extend(getContent());
        if (VaadinSession.getCurrent().getBrowser().isSafari()) {
            getContent().setValueChangeMode(ValueChangeMode.ON_BLUR);
        }
    }

    @Override
    public void setPrefixComponent(Component component) {
        getContent().setPrefixComponent(component);
    }

    @Override
    public void setSuffixComponent(Component component) {
        getContent().setSuffixComponent(component);
    }

    @Override
    public Tooltip setTooltipText(String text) {
        return getContent().setTooltipText(text);
    }

    @Override
    public Tooltip getTooltip() {
        return getContent().getTooltip();
    }

    @Override
    protected void setPresentationValue(BigDecimal newPresentationValue) {
        getContent().setValue(getNumberFormat().format(newPresentationValue));
    }

    public void addThemeVariants(TextFieldVariant... variants) {
        getContent().addThemeVariants(variants);
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        getContent().setErrorMessage(errorMessage);
    }

    @Override
    public String getErrorMessage() {
        return getContent().getErrorMessage();
    }

    @Override
    public void setInvalid(boolean invalid) {
        getContent().setInvalid(invalid);
    }

    @Override
    public boolean isInvalid() {
        return getContent().isInvalid();
    }

    @Override
    public void focus() {
        getContent().focus();
    }

    @Override
    public void setLabel(String label) {
        getContent().setLabel(label);
    }

    public Registration addValidationStatusChangeListener(ValidationStatusChangeListener<BigDecimal> listener) {
        return getContent().addClientValidatedEventListener((event) -> {
            listener.validationStatusChanged(new ValidationStatusChangeEvent(this, !this.isInvalid()));
        });
    }

    @Override
    public void setAriaLabel(String ariaLabel) {
        getContent().setAriaLabel(ariaLabel);
    }


    @Override
    public void setAriaLabelledBy(String ariaLabelledBy) {
        getContent().setAriaLabelledBy(ariaLabelledBy);
    }

}
