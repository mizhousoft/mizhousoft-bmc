import React, { useState, useEffect } from 'react';
import { useNavigate, Link, useParams } from 'react-router-dom';
import { Tree, Form, Button } from 'antd';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException, PageComponent } from '@/components/UIComponent';
import FormFlex from '@/constants/flex';
import { fetchRoleInfo } from '../redux/roleService';

const FormItem = Form.Item;
const { TreeNode } = Tree;

export default function RoleInfo() {
    const navigate = useNavigate();
    const { id } = useParams();

    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [uRole, setRole] = useState(undefined);
    const [uTreeData, setTreeData] = useState(undefined);

    const gotoList = () => {
        navigate('/role/list');
    };

    const renderTreeNodes = (data) =>
        data.map((item) => {
            if (item.children) {
                return (
                    <TreeNode title={item.title} key={item.key} dataRef={item}>
                        {renderTreeNodes(item.children)}
                    </TreeNode>
                );
            }
            return <TreeNode {...item} key={item.key} />;
        });

    useEffect(() => {
        const body = {
            id,
        };

        fetchRoleInfo(body).then(({ fetchStatus, role, treeData }) => {
            setRole(role);
            setTreeData(treeData);
            setFetchStatus(fetchStatus);
        });
    }, []);

    const pageTitle = (
        <>
            <Link to='/role/list'>角色</Link> / 查看角色
        </>
    );

    if (uFetchStatus.loading) {
        return <PageLoading title={pageTitle} />;
    }
    if (!uFetchStatus.okey) {
        return <PageException title={pageTitle} fetchStatus={uFetchStatus} goBack={gotoList} />;
    }

    const treeDataArray = JSON.parse(uTreeData);

    return (
        <PageComponent title={pageTitle}>
            <Form labelAlign='left'>
                <FormItem {...FormFlex.w100_lg3_required} label='角色名'>
                    {uRole.displayNameCN}
                </FormItem>
                <FormItem {...FormFlex.w100_lg3_required} label='描述'>
                    {uRole.descriptionCN}
                </FormItem>
                <div>角色权限：</div>
                <div className='mz_permission_tree'>
                    <Tree showLine defaultExpandAll>
                        {renderTreeNodes(treeDataArray)}
                    </Tree>
                </div>
                <div>
                    <Button type='primary' onClick={gotoList}>
                        返回
                    </Button>
                </div>
            </Form>
        </PageComponent>
    );
}
