/*
 * Copyright 2018 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.openehealth.ipf.commons.ihe.xds.rmux1;

import org.openehealth.ipf.commons.audit.AuditContext;
import org.openehealth.ipf.commons.audit.codes.EventActionCode;
import org.openehealth.ipf.commons.audit.model.AuditMessage;
import org.openehealth.ipf.commons.ihe.xds.core.audit.event.XdsPHIExportBuilder;
import org.openehealth.ipf.commons.ihe.xds.core.audit.codes.XdsEventTypeCode;
import org.openehealth.ipf.commons.ihe.xds.core.audit.XdsSubmitAuditDataset;
import org.openehealth.ipf.commons.ihe.xds.core.audit.XdsSubmitAuditStrategy30;

/**
 * Audit strategy for the actor "Update Initiator" of the RMU ITI-X1 transaction.
 *
 * @author Boris Stanojevic
 * @author Christian Ohr
 */
public class RmuX1ClientAuditStrategy extends XdsSubmitAuditStrategy30 {

    public RmuX1ClientAuditStrategy() {
        super(false);
    }


    @Override
    public AuditMessage[] makeAuditMessage(AuditContext auditContext, XdsSubmitAuditDataset auditDataset) {
        return new XdsPHIExportBuilder(auditContext, auditDataset, EventActionCode.Update,
                XdsEventTypeCode.RestrictedUpdateDocumentSet, auditDataset.getPurposesOfUse())
                .setPatient(auditDataset.getPatientId())
                .setSubmissionSetWithHomeCommunityId(auditDataset)
                .getMessages();
    }
}