
package com.codeminders.ardrone.video;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

// Copyright � 2007-2011, PARROT SA, all rights reserved.

// DISCLAIMER
// The APIs is provided by PARROT and contributors "AS IS" and any express or
// implied warranties, including, but not limited to, the implied warranties of
// merchantability
// and fitness for a particular purpose are disclaimed. In no event shall PARROT
// and contributors be liable for any direct, indirect, incidental, special,
// exemplary, or
// consequential damages (including, but not limited to, procurement of
// substitute goods or services; loss of use, data, or profits; or business
// interruption) however
// caused and on any theory of liability, whether in contract, strict liability,
// or tort (including negligence or otherwise) arising in any way out of the use
// of this
// software, even if advised of the possibility of such damage.

// Author : Daniel Schmidt
// Publishing date : 2010-01-06
// based on work by : Wilke Jansoone

// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
// - Redistributions of source code must retain the above copyright notice, this
// list of conditions, the disclaimer and the original author of the source
// code.
// - Neither the name of the PixVillage Team, nor the names of its contributors
// may be used to endorse or promote products derived from this software without
// specific prior written permission.

public class uint
{

    public String toString()
    {
        return Integer.toString(base2, 2);
    }

    public uint(int base)
    {
        this.base2 = base;

    }

    public uint(uint that)
    {
        this.base2 = that.base2;
    }

    public uint(byte[] bp, int start)
    {
        try
        {
            byte[] b = new byte[4];
            b[0] = bp[start + 3];
            b[1] = bp[start + 2];
            b[2] = bp[start + 1];
            b[3] = bp[start + 0];

            ByteArrayInputStream bas = new ByteArrayInputStream(b);
            DataInputStream din = new DataInputStream(bas);

            this.base2 = din.readInt();
        } catch(Exception e)
        {
            throw new RuntimeException("error creating uint", e);
        }
    }

    private int base2;

    public short times(short i)
    {
        return (short) (intValue() * i);
    }

    public uint shiftRight(int i)
    {
        int base = base2;
        base = base >>> i;
        return new uint(base);
    }

    public uint shiftLeft(int i)
    {
        int base = base2;
        base <<= i;

        return new uint(base);
    }

    public int flipBits()
    {
        int base = ~base2;

        return base;
    }

    public int intValue()
    {
        return base2;

    }

    public uint and(int andval)
    {
        int retval = base2 & andval;
        return new uint(retval);
    }

    public void shiftLeftEquals(int i)
    {
        int base = base2;
        base <<= i;
        base2 = base;
    }

    public void shiftRightEquals(int i)
    {
        int base = base2;
        base >>>= i;
        base2 = base;
    }

    public uint or(uint orval)
    {
        int retval = base2 | orval.base2;
        return new uint(retval);
    }

    public byte[] getBytes()
    {
        try
        {
            ByteArrayOutputStream bas = new ByteArrayOutputStream();
            DataOutputStream dout = new DataOutputStream(bas);

            dout.writeInt(base2);
            dout.close();

            byte[] ba = bas.toByteArray();

            if(ba.length != 4)
                throw new RuntimeException("somehow got " + ba.length + " bytes instead of 4 bytes from int " + base2);

            byte[] b = new byte[4];
            b[0] = ba[3];
            b[1] = ba[2];
            b[2] = ba[1];
            b[3] = ba[0];

            return b;
        } catch(Exception e)
        {
            throw new RuntimeException("error in uint getBytes", e);
        }
    }
}
