/**
 * Hidden Parameter Definition
 */
package com.wangyin.parameter;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.Util;
import hudson.model.*;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.DoNotUse;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

/**
 * @author wy-scm <wy-scm@jd.com>
 * 
 */
@SuppressWarnings("unused")
public class WHideParameterDefinition extends SimpleParameterDefinition {

	private static final long serialVersionUID = 8296806777255584941L;
	private String defaultValue = "";

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@DataBoundConstructor
	public WHideParameterDefinition(String name,String defaultValue, String description) {
		super(name, description);
		this.defaultValue = defaultValue;
	}

    @Extension
    @Symbol({"hidden", "hiddenParam"})
	public static class DescriptorImpl extends ParameterDescriptor {
        @Override
        @NonNull
        public String getDisplayName() {
            return "Hidden Parameter";
        }
    }

	@Override
	public WHideParameterValue getDefaultParameterValue() {
		return new WHideParameterValue(getName(), defaultValue, getDescription());
	}

	@Override
	public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
		WHideParameterValue value = req.bindJSON(WHideParameterValue.class, jo);
        value.setDescription(getDescription());
		return value;
	}

	public ParameterValue createValue(String str) {
		return new WHideParameterValue(getName(), str, getDescription());
	}

    @Override
    public ParameterDefinition copyWithDefaultValue(ParameterValue defaultValue) {
        if (defaultValue instanceof StringParameterValue) {
            StringParameterValue value = (StringParameterValue) defaultValue;
            return new WHideParameterDefinition(getName(), value.getValue(), getDescription());
        } else {
            return this;
        }
    }
}
