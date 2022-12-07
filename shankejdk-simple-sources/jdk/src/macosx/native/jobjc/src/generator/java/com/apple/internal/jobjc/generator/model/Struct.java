/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.model;

import java.util.List;

import org.w3c.dom.Node;

import com.apple.internal.jobjc.generator.classes.OutputFile;
import com.apple.internal.jobjc.generator.classes.StructClassFile;
import com.apple.internal.jobjc.generator.model.types.Type;
import com.apple.internal.jobjc.generator.model.types.NType.NField;
import com.apple.internal.jobjc.generator.model.types.NType.NStruct;
import com.apple.internal.jobjc.generator.utils.Fp;
import com.apple.internal.jobjc.generator.utils.QA;
import com.apple.internal.jobjc.generator.utils.Fp.Map2;

/**
 * A struct has the following restrictions:
 *
 *    - type32.fields.count == type64.fields.count
 *    - forAll i: type32.field[i].name == type64.field[i].name
 *    - forAll i: type32.field[i].class == type64.field[i].class
 */
public class Struct extends TypeElement<Framework> implements OutputFileGenerator {
    public final List<Field> fields;
    public static class Field{
        public final String name;
        public final Type type;
        public final NField field32, field64;
        public Field(String name, NField field32, NField field64) {
            QA.nonNull(name);
            this.name = name;
            // TODO <field> really should have a declared_type attr. See if BS patch is possible.
            this.type = Type.getType(null, field32.type, field64.type);
            this.field32 = field32;
            this.field64 = field64;
        }
    }

    public Struct(final Node node, final Framework parent) throws Throwable {
        super(node, getAttr(node, "name"), parent);
        NStruct nstruct32 = (NStruct) type.type32;
        NStruct nstruct64 = (NStruct) type.type64;
        this.fields = Fp.map2(new Map2<NField,NField,Field>(){
            public Field apply(NField f32, NField f64) {
                assert f32.name.equals(f64.name);
                return new Field(f32.name, f32, f64);
            }
        }, nstruct32.fields, nstruct64.fields);
    }

    public void generateClasses(final List<OutputFile> generatedClassFiles) {
        generatedClassFiles.add(new StructClassFile(this));
    }
}
