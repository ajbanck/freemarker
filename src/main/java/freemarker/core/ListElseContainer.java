/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package freemarker.core;

import java.io.IOException;

import freemarker.template.TemplateException;

class ListElseContainer extends TemplateElement {

    private final IteratorBlock listPart;
    private final ElseOfList elsePart;

    public ListElseContainer(IteratorBlock listPart, ElseOfList elsePart) {
        setRegulatedChildBufferCapacity(2);
        addRegulatedChild(listPart);
        addRegulatedChild(elsePart);
        this.listPart = listPart;
        this.elsePart = elsePart;
    }

    @Override
    void accept(Environment env) throws TemplateException, IOException {
        if (!listPart.acceptWithResult(env)) {
            elsePart.accept(env);
        }
    }

    @Override
    boolean isNestedBlockRepeater() {
        return false;
    }

    @Override
    protected String dump(boolean canonical) {
        if (canonical) {
            StringBuilder buf = new StringBuilder();
            int ln = getRegulatedChildCount();
            for (int i = 0; i < ln; i++) {
                TemplateElement element = getRegulatedChild(i);
                buf.append(element.dump(canonical));
            }
            buf.append("</#list>");
            return buf.toString();
        } else {
            return getNodeTypeSymbol();
        }
    }

    @Override
    String getNodeTypeSymbol() {
        return "#list-#else-container";
    }

    @Override
    int getParameterCount() {
        return 0;
    }

    @Override
    Object getParameterValue(int idx) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    ParameterRole getParameterRole(int idx) {
        throw new IndexOutOfBoundsException();
    }

}
