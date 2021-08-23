package com.zpmc.ztos.infra.base.common.model;

import com.zpmc.ztos.infra.base.business.interfaces.*;
import com.zpmc.ztos.infra.base.common.contexts.PortalApplicationContext;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Locale;

public abstract class AbstractTranslationContext implements ITranslationContext {
    IFieldConverter _fieldConverter;
    private IMetafieldDictionary _metafieldDictionary;
    private IDateConverter _dateConverter;
    protected IMessageTranslator _messageTranslator;
    public static final Logger LOGGER = Logger.getLogger(AbstractTranslationContext.class);

    @Override
    public IMessageTranslator getMessageTranslator() {
        if (this._messageTranslator == null) {
            Locale locale = this.getLocale();
            IMessageTranslatorProvider translatorProvider = (IMessageTranslatorProvider) PortalApplicationContext.getBean("messageTranslatorProvider");
            this._messageTranslator = translatorProvider.createMessageTranslator(locale, this.getUserContext());
        }
        return this._messageTranslator;
    }

    @Override
    public boolean isMessageTranslatorAvailable() {
        return this._messageTranslator != null;
    }

    public abstract Object getBean(String var1);

    @Override
    public IMetafieldDictionary getIMetafieldDictionary() {
        if (this._metafieldDictionary == null || this._metafieldDictionary.isStale()) {
            UserContext userContext = this.getUserContext();
            if (userContext != null) {
                ScopeCoordinates scopeCoordinates = userContext.getScopeCoordinate();
//                IMetafieldDictionaryScopedProvider scopedProvider = (IMetafieldDictionaryScopedProvider)this.getBean("MetafieldDictionaryProvider");
//                this._metafieldDictionary = scopedProvider.getMetafieldDictionary(scopeCoordinates);
            } else {
                LOGGER.info((Object)"Requested metafield dictionary before user context was set. Using global dictionary");
//                MetafieldDictionaryProvider mfdpProvider = (MetafieldDictionaryProvider)this.getBean("GlobalMetafieldDictionaryProvider");
//                return mfdpProvider.getMetafieldDictionary();
            }
        }
        return this._metafieldDictionary;
    }

    @Override
    public IFieldConverter getFieldConverter() {
        if (this._fieldConverter == null) {
//            this._fieldConverter = InputValidator.getInputValidator(this.getIMetafieldDictionary());
        }
        return this._fieldConverter;
    }

    @Override
    public IDateConverter getDateConverter() {
        if (this._dateConverter == null) {
            String pattern = null;
            try {
                pattern = FrameworkConfig.DATE_TIME_DISPLAY_FORMAT.getSetting(this.getUserContext());
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                if (StringUtils.isEmpty((String)pattern)) {
                    pattern = (String)FrameworkConfig.DATE_TIME_DISPLAY_FORMAT.getConfigDefaultValue();
                }
            }
            catch (Exception e) {
                pattern = (String)FrameworkConfig.DATE_TIME_DISPLAY_FORMAT.getConfigDefaultValue();
            }
  //          this._dateConverter = new DefaultDateConverter(pattern, this.getUserContext().getTimeZone());
        }
        return this._dateConverter;
    }

    @Override
    public abstract UserContext getUserContext();

}
