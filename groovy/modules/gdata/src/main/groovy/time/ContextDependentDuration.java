/*
 * Created on Apr 21, 2006
 *
 * Copyright 2006 John G. Wilson
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package groovy.time;

import java.util.Calendar;
import java.util.Date;

/**
 * @author John Wilson tug@wilson.co.uk
 *
 * ContextDependentDuration represents durations whose length in milliseconds cannot be determined withou knowing the datum point.
 *
 * I don't know how many days in a year unless I know if it's a leap year or not.
 * 
 * I don't know how many days in a month unless I know the name of the month (and if it's a leap yaer if the month is February)
 * 
 */
public class ContextDependentDuration extends BaseDuration {
    private final int years;
    private final int months;
    
    public ContextDependentDuration(final int years, final int months, final int days, final int hours, final int minutes, final int seconds, final int millis) {
        super(days, hours, minutes, seconds, millis);
        this.years = years;
        this.months = months;
    }

    public int getMonths() {
        return this.months;
    }

    public int getYears() {
        return this.years;
    }
    
    public ContextDependentDuration plus(final ContextDependentDuration rhs) {
        return new ContextDependentDuration(this.getYears() + rhs.getYears(), this.getMonths() + rhs.getMonths(),
                                            this.getDays() + rhs.getDays(), this.getHours() + rhs.getHours(),
                                            this.getMinutes() + rhs.getMinutes(), this.getSeconds() + rhs.getSeconds(),
                                            this.getMillis() + rhs.getMillis());
    }
    
    public ContextDependentDuration plus(final Duration rhs) {
        return new ContextDependentDuration(this.getYears(), this.getMonths(),
                                            this.getDays() + rhs.getDays(), this.getHours() + rhs.getHours(),
                                            this.getMinutes() + rhs.getMinutes(), this.getSeconds() + rhs.getSeconds(),
                                            this.getMillis() + rhs.getMillis());

    }
    
    public ContextDependentDuration minus(final ContextDependentDuration rhs) {
        return new ContextDependentDuration(this.getYears() - rhs.getYears(), this.getMonths() - rhs.getMonths(),
                                            this.getDays() - rhs.getDays(), this.getHours() - rhs.getHours(),
                                            this.getMinutes() - rhs.getMinutes(), this.getSeconds() - rhs.getSeconds(),
                                            this.getMillis() - rhs.getMillis());

    }
    
    public ContextDependentDuration minus(final Duration rhs) {
        return new ContextDependentDuration(this.getYears(), this.getMonths(),
                                            this.getDays() - rhs.getDays(), this.getHours() - rhs.getHours(),
                                            this.getMinutes() - rhs.getMinutes(), this.getSeconds() - rhs.getSeconds(),
                                            this.getMillis() - rhs.getMillis());

    }
    
    public Date getAgo() {
    final Calendar cal = Calendar.getInstance();

        cal.add(Calendar.YEAR, -this.getYears());
        cal.add(Calendar.MONTH, -this.getMonths());
        cal.add(Calendar.DAY_OF_YEAR, -this.getDays());
        cal.add(Calendar.HOUR_OF_DAY, -this.getHours());
        cal.add(Calendar.MINUTE, -this.getMinutes());
        cal.add(Calendar.SECOND, -this.getSeconds());
        cal.add(Calendar.MILLISECOND, -this.getMillis());
        
        
        // TODO: work out when to return java.sql.Date instead
        return cal.getTime();
    }
    
    public From getFrom() {
        return new From() {
            public Date getNow() {
            final Calendar cal = Calendar.getInstance();

                cal.add(Calendar.YEAR, ContextDependentDuration.this.getYears());
                cal.add(Calendar.MONTH, ContextDependentDuration.this.getMonths());
                cal.add(Calendar.DAY_OF_YEAR, ContextDependentDuration.this.getDays());
                cal.add(Calendar.HOUR_OF_DAY, ContextDependentDuration.this.getHours());
                cal.add(Calendar.MINUTE, ContextDependentDuration.this.getMinutes());
                cal.add(Calendar.SECOND, ContextDependentDuration.this.getSeconds());
                cal.add(Calendar.MILLISECOND, ContextDependentDuration.this.getMillis());
                
                return cal.getTime();
             }
            
            public java.sql.Date getToday() {
            final Calendar cal = Calendar.getInstance();

                cal.add(Calendar.YEAR, ContextDependentDuration.this.years);
                cal.add(Calendar.MONTH, ContextDependentDuration.this.months);
                cal.add(Calendar.DAY_OF_YEAR, ContextDependentDuration.this.getDays());
                cal.set(Calendar.HOUR_OF_DAY, ContextDependentDuration.this.getHours());
                cal.set(Calendar.MINUTE, ContextDependentDuration.this.getMinutes());
                cal.set(Calendar.SECOND, ContextDependentDuration.this.getSeconds());
                cal.set(Calendar.MILLISECOND, ContextDependentDuration.this.getMillis());
                
                return new java.sql.Date(cal.getTimeInMillis());
            }
        };
    }
}
