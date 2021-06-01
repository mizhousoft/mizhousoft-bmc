import React, { Component } from 'react';
import { withRouter, Link } from 'react-router-dom';
import { Tree, Form, Button, Input, message } from 'antd';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException } from '@/components/UIComponent';
import FormFlex from '@/constants/flex';
import { editRole, modifyRole } from '../redux/roleService';

const FormItem = Form.Item;
const { TreeNode } = Tree;
const { TextArea } = Input;

class EditRole extends Component {
    formRef = React.createRef();

    constructor(props) {
        super(props);
        this.state = {
            fetchStatus: LOADING_FETCH_STATUS,
            confirmLoading: false,

            id: props.match.params.id,

            role: undefined,
            treeData: undefined,
            checkedKeys: [],
        };
    }

    gotoList = () => {
        const { history } = this.props;
        history.push('/role/list');
    };

    onCheck = (checkedKeys) => {
        this.setState({ checkedKeys });
    };

    renderTreeNodes = (data) =>
        data.map((item) => {
            if (item.children) {
                return (
                    <TreeNode title={item.title} key={item.key} dataRef={item}>
                        {this.renderTreeNodes(item.children)}
                    </TreeNode>
                );
            }
            return <TreeNode {...item} key={item.key} />;
        });

    onFinish = (values) => {
        const { checkedKeys, id } = this.state;

        if (checkedKeys.length === 0) {
            message.error('请选择角色权限');
            return;
        }

        const form = {
            id,
            name: values.name,
            description: values.description,
            permIds: checkedKeys,
        };

        this.setState({ confirmLoading: true });

        modifyRole(form).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('修改角色成功。');
                this.gotoList();
            } else {
                this.setState({ confirmLoading: false });
                message.error(fetchStatus.message);
            }
        });
    };

    componentDidMount() {
        const body = {
            id: this.state.id,
        };

        editRole(body).then(({ fetchStatus, role, treeData, checkedKeys }) => {
            this.setState({
                fetchStatus,
                role,
                treeData,
                checkedKeys: checkedKeys ?? [],
            });
        });
    }

    renderBody() {
        const { fetchStatus, role, treeData, checkedKeys, confirmLoading } = this.state;

        if (fetchStatus.loading) {
            return <PageLoading />;
        }
        if (!fetchStatus.okey) {
            return <PageException fetchStatus={fetchStatus} goBack={this.gotoList} />;
        }

        const treeDataArray = JSON.parse(treeData);

        return (
            <Form
                onFinish={this.onFinish}
                ref={this.formRef}
                labelAlign='left'
                initialValues={{
                    name: role.displayNameCN,
                    description: role.descriptionCN,
                }}
            >
                <FormItem
                    name='name'
                    {...FormFlex.w100_lg4_required}
                    label='角色名'
                    rules={[
                        {
                            required: true,
                            message: '请输入角色名',
                        },
                        {
                            min: 2,
                            message: '角色名最小长度是2',
                        },
                        {
                            max: 15,
                            message: '角色名最大长度是15',
                        },
                    ]}
                >
                    <Input autoComplete='off' maxLength='15' />
                </FormItem>
                <FormItem name='description' {...FormFlex.w100_lg4} label='描述'>
                    <TextArea rows={1} maxLength='255' />
                </FormItem>
                <div>角色权限：</div>
                <div className='mz_permission_tree'>
                    <Tree
                        showLine
                        checkable
                        defaultExpandAll
                        defaultCheckedKeys={checkedKeys}
                        onExpand={this.onExpand}
                        onSelect={this.onSelect}
                        onCheck={this.onCheck}
                    >
                        {this.renderTreeNodes(treeDataArray)}
                    </Tree>
                </div>
                <div className='mz-button-group'>
                    <Button type='primary' htmlType='submit' loading={confirmLoading}>
                        确定
                    </Button>
                    <Button onClick={this.gotoList}>取消</Button>
                </div>
            </Form>
        );
    }

    render() {
        return (
            <>
                <div className='mz-page-head'>
                    <div className='title'>
                        <Link to='/role/list'>角色</Link> / 编辑角色
                    </div>
                </div>

                <div className='mz-page-content'>
                    <div className='mz-page-content-body'>{this.renderBody()}</div>
                </div>
            </>
        );
    }
}

export default withRouter(EditRole);
