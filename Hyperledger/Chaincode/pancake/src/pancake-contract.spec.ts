/*
 * SPDX-License-Identifier: Apache-2.0
 */

import { Context } from 'fabric-contract-api';
import { ChaincodeStub, ClientIdentity } from 'fabric-shim';
import { PancakeContract } from '.';

import * as chai from 'chai';
import * as chaiAsPromised from 'chai-as-promised';
import * as sinon from 'sinon';
import * as sinonChai from 'sinon-chai';
import winston = require('winston');

chai.should();
chai.use(chaiAsPromised);
chai.use(sinonChai);

class TestContext implements Context {
    public stub: sinon.SinonStubbedInstance<ChaincodeStub> = sinon.createStubInstance(ChaincodeStub);
    public clientIdentity: sinon.SinonStubbedInstance<ClientIdentity> = sinon.createStubInstance(ClientIdentity);
    public logging = {
        getLogger: sinon.stub().returns(sinon.createStubInstance(winston.createLogger().constructor)),
        setLevel: sinon.stub(),
     };
}

describe('PancakeContract', () => {

    let contract: PancakeContract;
    let ctx: TestContext;

    beforeEach(() => {
        contract = new PancakeContract();
        ctx = new TestContext();
        ctx.stub.getState.withArgs('1001').resolves(Buffer.from('{"value":"pancake 1001 value"}'));
        ctx.stub.getState.withArgs('1002').resolves(Buffer.from('{"value":"pancake 1002 value"}'));
    });

    describe('#pancakeExists', () => {

        it('should return true for a pancake', async () => {
            await contract.pancakeExists(ctx, '1001').should.eventually.be.true;
        });

        it('should return false for a pancake that does not exist', async () => {
            await contract.pancakeExists(ctx, '1003').should.eventually.be.false;
        });

    });

    describe('#createPancake', () => {

        it('should create a pancake', async () => {
            await contract.createPancake(ctx, '1003', 'pancake 1003 value');
            ctx.stub.putState.should.have.been.calledOnceWithExactly('1003', Buffer.from('{"value":"pancake 1003 value"}'));
        });

        it('should throw an error for a pancake that already exists', async () => {
            await contract.createPancake(ctx, '1001', 'myvalue').should.be.rejectedWith(/The pancake 1001 already exists/);
        });

    });

    describe('#readPancake', () => {

        it('should return a pancake', async () => {
            await contract.readPancake(ctx, '1001').should.eventually.deep.equal({ value: 'pancake 1001 value' });
        });

        it('should throw an error for a pancake that does not exist', async () => {
            await contract.readPancake(ctx, '1003').should.be.rejectedWith(/The pancake 1003 does not exist/);
        });

    });

    describe('#updatePancake', () => {

        it('should update a pancake', async () => {
            await contract.updatePancake(ctx, '1001', 'pancake 1001 new value');
            ctx.stub.putState.should.have.been.calledOnceWithExactly('1001', Buffer.from('{"value":"pancake 1001 new value"}'));
        });

        it('should throw an error for a pancake that does not exist', async () => {
            await contract.updatePancake(ctx, '1003', 'pancake 1003 new value').should.be.rejectedWith(/The pancake 1003 does not exist/);
        });

    });

    describe('#deletePancake', () => {

        it('should delete a pancake', async () => {
            await contract.deletePancake(ctx, '1001');
            ctx.stub.deleteState.should.have.been.calledOnceWithExactly('1001');
        });

        it('should throw an error for a pancake that does not exist', async () => {
            await contract.deletePancake(ctx, '1003').should.be.rejectedWith(/The pancake 1003 does not exist/);
        });

    });

});
