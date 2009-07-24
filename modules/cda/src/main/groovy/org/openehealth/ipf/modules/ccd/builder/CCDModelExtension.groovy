/*
 * Copyright 2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openehealth.ipf.modules.ccd.builder

import org.openehealth.ipf.modules.cda.CDAR2Renderer
import org.openhealthtools.ihe.common.cdar2.*
import java.lang.Boolean
import org.openhealthtools.ihe.common.cdar2.XActRelationshipEntry
import org.openhealthtools.ihe.common.cdar2.CDAR2Factory
import org.openhealthtools.ihe.common.cdar2.XServiceEventPerformer
import org.openhealthtools.ihe.common.cdar2.POCDMT000040Act
import org.openhealthtools.ihe.common.cdar2.POCDMT000040EntryRelationship
import org.openhealthtools.ihe.common.cdar2.XActRelationshipEntryRelationship
import org.openhealthtools.ihe.common.cdar2.XParticipationAuthorPerformer
import org.openhealthtools.ihe.common.cdar2.POCDMT000040Participant1
import org.openhealthtools.ihe.common.cdar2.ParticipationAncillary
import org.openhealthtools.ihe.common.cdar2.ParticipationIndirectTarget
import org.openhealthtools.ihe.common.cdar2.ParticipationPhysicalPerformer
import org.openhealthtools.ihe.common.cdar2.POCDMT000040Organizerimport org.openhealthtools.ihe.common.cdar2.POCDMT000040RelatedSubjectimport org.openhealthtools.ihe.common.cdar2.POCDMT000040Subjectimport org.openhealthtools.ihe.common.cdar2.POCDMT000040Observationimport org.openehealth.ipf.modules.cda.builder.BaseModelExtension
import org.eclipse.emf.ecore.xml.type.XMLTypePackage
import org.eclipse.emf.ecore.util.FeatureMap
import org.eclipse.emf.ecore.util.FeatureMapUtil
import org.eclipse.emf.ecore.xmi.XMLResource
import org.eclipse.emf.common.util.AbstractEnumerator

/**
 * Make sure that the CDAModelExtensions are called before
 *
 * @author Christian Ohr
 * @deprecated
 */
public class CCDModelExtension extends BaseModelExtension{

     CCDModelExtension() {
         super()
     }
     
     CCDModelExtension(builder) {
         super(builder)
     }

    def extensions = {

        // --------------------------------------------------------------------------------------------
        // Chapter 2.1 Header representation
        // --------------------------------------------------------------------------------------------
        // CONF-2: A CCD SHALL contain exactly one ClinicalDocument / documentationOf / serviceEvent.
        // CONF-3: The value for �ClinicalDocument / documentationOf / serviceEvent
        //         / @classCode� SHALL be �PCPR� �Care provision� 2.16.840.1.113883.5.6 ActClass STATIC.
        // CONF-4: ClinicalDocument / documentationOf / serviceEvent SHALL contain
        //         exactly one serviceEvent / effectiveTime / low and exactly one
        //         serviceEvent / effectiveTime / high.
        POCDMT000040ClinicalDocument.metaClass {

            setMainActivity {POCDMT000040ServiceEvent serviceEvent ->
                POCDMT000040DocumentationOf docOf = CDAR2Factory.eINSTANCE.createPOCDMT000040DocumentationOf()
                docOf.serviceEvent = serviceEvent
                delegate.documentationOf.add(docOf)
            }

            getMainActivity { ->
                delegate.documentationOf.find { it.serviceEvent.classCode.name == 'PCPR' } ?.serviceEvent
            }

        }// ccd header extensions
           
        return 1
        
    }//ccd extensions 
    
    
    String templateId() {
        '2.16.840.1.113883.10.20.1'
    }
    
    String extensionName() {
        "Continuity of Care Document (CCD)"
    }    
}
