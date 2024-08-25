/* eslint-disable no-unused-vars */
import SendIcon from '@mui/icons-material/Send';
import { Box, FormControl, Grid, IconButton, InputAdornment, OutlinedInput } from '@mui/material';
import { Stomp } from '@stomp/stompjs';
import { useEffect, useState } from 'react';
import { Card, CardBody, CardFooter } from 'react-bootstrap';
import SockJS from 'sockjs-client';

import { useForm } from 'react-hook-form';
import { handleGetChatMessages, handleGetMyInfoAPI } from '~/apis';
import './index.css';

var stompClient = null;
export default function Chat() {
    const userId = localStorage.getItem('userId');
    const role = localStorage.getItem('role');
    const { register, handleSubmit } = useForm();
    const [chats, setChats] = useState(new Map());
    const [tab, setTab] = useState();
    const [historyMessage, setHistoryMessage] = useState([]);

    const [userData, setUserData] = useState({
        userId: userId,
        message: '',
    });

    const handleMessage = (event) => {
        const { value } = event.target;
        setUserData({ ...userData, message: value });
    };

    const registerUser = () => {
        let socket = new SockJS('http://localhost:8082/httc-sport-ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, () => onConnected(stompClient), onError);
    };

    const onConnected = (stompClient) => {
        const destination = role === 'ADMIN' ? '/topic/chat/admin' : `/topic/chat/${userId}`;
        stompClient.subscribe(destination, onMessageReceived);
    };

    const onError = (error) => {
        console.error('WebSocket connection error:', error);
    };

    const getChatRoom = async () => {
        try {
            const res = await handleGetMyInfoAPI(userId);
            // console.log(res);
            // setHistoryMessage(res.data.result.chatRooms[0].roomId);
        } catch (error) {
            console.error('Error fetching chat room info:', error);
        }
    };

    const getHistoryMessages = async () => {
        try {
            const roomId = historyMessage.roomId;
            const res = await handleGetChatMessages(roomId);
            console.log(res);
        } catch (error) {
            console.error('Error fetching chat history:', error);
        }
    };

    const onMessageReceived = (payload) => {
        let payloadData = JSON.parse(payload);
        if (chats.get(payloadData.userId)) {
            chats.get(payloadData.userId).push(payloadData);
            setChats(new Map(chats));
        } else {
            let list = [];
            list.push(payloadData);
            chats.set(payloadData.userId, list);
            setChats(new Map(chats));
        }
    };

    // Chat GPT toi uu
    // const onMessageReceived = (payload) => {
    //     const payloadData = JSON.parse(payload.body);
    //     setChats((prevChats) => {
    //         const updatedChats = new Map(prevChats);
    //         const chatList = updatedChats.get(payloadData.userId) || [];
    //         chatList.push(payloadData);
    //         updatedChats.set(payloadData.userId, chatList);
    //         return updatedChats;
    //     });
    // };

    const sendMessage = () => {
        console.log(userData.message);
        if (stompClient) {
            if (role === 'USER') {
                let chatMessage = {
                    userId: userData.userId,
                    message: userData.message,
                };
                stompClient.send(`/app/chat/${userId}`, {}, JSON.stringify(chatMessage));
                setUserData({ ...userData, message: '' });
            } else {
                let chatMessage = {
                    userId: tab,
                    message: userData.message,
                };
                stompClient.send(`/app/chat/admin`, {}, JSON.stringify(chatMessage));
                setUserData({ ...userData, message: '' });
            }
        }
    };

    useEffect(() => {
        registerUser();
        getChatRoom();
    });

    return (
        <Box sx={{ p: 1 }}>
            <Grid container>
                <Grid item xs={4}>
                    <Box
                        component={Card}
                        sx={{
                            borderRadius: 0,
                            width: '280px',
                            height: '600px',
                            backgroundColor: 'lightsteelblue',
                        }}
                    >
                        <CardBody>
                            <ul>
                                {[...chats.keys()].map((name, index) => (
                                    <li
                                        onClick={() => {
                                            setTab(name);
                                        }}
                                        key={index}
                                    >
                                        {name}
                                    </li>
                                ))}
                            </ul>
                        </CardBody>
                    </Box>
                </Grid>
                <Grid item xs={8}>
                    <Box
                        className="card"
                        component="form"
                        onSubmit={handleSubmit(sendMessage)}
                        sx={{ borderRadius: 0, width: '550px', height: '600px' }}
                    >
                        <CardBody>
                            <ul>
                                {/* {[
                                ...chats.get(tab).map((chat, index) => (
                                    <li className="message" key={index}>
                                        <div>{chat.userId}</div>
                                        <div>{chat.message}</div>
                                    </li>
                                )),
                            ]} */}
                                {(chats.get(tab) || []).map((chat, index) => (
                                    <li className="message" key={index}>
                                        <div>{chat.userId}</div>
                                        <div>{chat.message}</div>
                                    </li>
                                ))}
                            </ul>
                        </CardBody>
                        <CardFooter>
                            <FormControl fullWidth variant="outlined">
                                <OutlinedInput
                                    value={userData.message}
                                    onChange={handleMessage}
                                    endAdornment={
                                        <InputAdornment position="end">
                                            <IconButton onClick={sendMessage} edge="end">
                                                <SendIcon color="info" />
                                            </IconButton>
                                        </InputAdornment>
                                    }
                                    placeholder={`Nhập nội dung tin nhắn...`}
                                />
                            </FormControl>
                        </CardFooter>
                    </Box>
                </Grid>
            </Grid>
        </Box>
    );
}
