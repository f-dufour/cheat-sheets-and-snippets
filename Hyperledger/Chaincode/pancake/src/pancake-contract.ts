/*
 * SPDX-License-Identifier: Apache-2.0
 */

import { Context, Contract, Info, Returns, Transaction } from 'fabric-contract-api';
import { Pancake } from './pancake';

@Info({title: 'PancakeContract', description: 'My Smart Contract' })
export class PancakeContract extends Contract {

    @Transaction(false)
    @Returns('boolean')
    public async pancakeExists(ctx: Context, pancakeId: string): Promise<boolean> {
        const buffer = await ctx.stub.getState(pancakeId);
        return (!!buffer && buffer.length > 0);
    }

    @Transaction()
    public async createPancake(ctx: Context, pancakeId: string, value: string): Promise<void> {
        const exists = await this.pancakeExists(ctx, pancakeId);
        if (exists) {
            throw new Error(`The pancake ${pancakeId} already exists`);
        }
        const pancake = new Pancake();
        pancake.value = value;
        const buffer = Buffer.from(JSON.stringify(pancake));
        await ctx.stub.putState(pancakeId, buffer);
    }

    @Transaction(false)
    @Returns('Pancake')
    public async readPancake(ctx: Context, pancakeId: string): Promise<Pancake> {
        const exists = await this.pancakeExists(ctx, pancakeId);
        if (!exists) {
            throw new Error(`The pancake ${pancakeId} does not exist`);
        }
        const buffer = await ctx.stub.getState(pancakeId);
        const pancake = JSON.parse(buffer.toString()) as Pancake;
        return pancake;
    }

    @Transaction()
    public async updatePancake(ctx: Context, pancakeId: string, newValue: string): Promise<void> {
        const exists = await this.pancakeExists(ctx, pancakeId);
        if (!exists) {
            throw new Error(`The pancake ${pancakeId} does not exist`);
        }
        const pancake = new Pancake();
        pancake.value = newValue;
        const buffer = Buffer.from(JSON.stringify(pancake));
        await ctx.stub.putState(pancakeId, buffer);
    }

    @Transaction()
    public async deletePancake(ctx: Context, pancakeId: string): Promise<void> {
        const exists = await this.pancakeExists(ctx, pancakeId);
        if (!exists) {
            throw new Error(`The pancake ${pancakeId} does not exist`);
        }
        await ctx.stub.deleteState(pancakeId);
    }

}
