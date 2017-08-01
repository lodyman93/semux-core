/*
 * Copyright (c) 2017 The Semux Developers
 *
 * Distributed under the MIT software license, see the accompanying file
 * LICENSE or https://opensource.org/licenses/mit-license.php
 */
package org.semux.core;

import org.semux.crypto.Hex;
import org.semux.utils.Bytes;

public class Delegate {
    protected byte[] addr;
    protected byte[] name;
    protected long vote;

    public Delegate(byte[] addr, byte[] name, long vote) {
        this.addr = addr;
        this.name = name;
        this.vote = vote;
    }

    public byte[] getAddress() {
        return addr;
    }

    public byte[] getName() {
        return name;
    }

    public String getNameStr() {
        return Bytes.toString(name);
    }

    public long getVote() {
        return vote;
    }

    public String toPeerId() {
        return Hex.encode(addr);
    }
}
