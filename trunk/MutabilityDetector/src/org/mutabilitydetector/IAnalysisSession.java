/* 
 * Mutability Detector
 *
 * Copyright 2009 Graham Allan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.mutabilitydetector;

import java.util.Collection;
import java.util.Collections;

import org.mutabilitydetector.checkers.info.AnalysisDatabase;

public interface IAnalysisSession {

	IsImmutable isImmutable(String className);
	
	AnalysisResult resultFor(String className);
	
	void addAnalysisResult(AnalysisResult result);
	void addAnalysisError(AnalysisError error);
	
	void runAnalysis(Collection<String> classNames);
	
	Collection<AnalysisResult> getResults();
	Collection<AnalysisError> getErrors();
	
	AnalysisDatabase analysisDatabase();
	
	public static enum IsImmutable {
		COULD_NOT_ANALYSE,
		DEFINITELY,
		MAYBE,
		DEFINITELY_NOT;
		
	}

	public static final class AnalysisResult {
		public final String dottedClassName;
		public final IsImmutable isImmutable;
		public final Collection<CheckerReasonDetail> reasons;
		public AnalysisResult(String dottedClassName, IsImmutable isImmutable, Collection<CheckerReasonDetail> reasons) {
			this.dottedClassName = dottedClassName;
			this.isImmutable = isImmutable;
			this.reasons = Collections.unmodifiableCollection(reasons);
		}
		
		public static AnalysisResult definitelyImmutable(String dottedClassName) {
			return new AnalysisResult(dottedClassName, IsImmutable.DEFINITELY, Collections.<CheckerReasonDetail>emptyList());
		}
	}
	
	public static final class AnalysisError {
		public final String checkerName;
		public final String description;
		public final String onClass;
		public AnalysisError(String onClass, String checkerName, String errorDescription) {
			this.onClass = onClass;
			this.checkerName = checkerName;
			this.description = errorDescription;
			
		}
	}



	
}


