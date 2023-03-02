import React from 'react'
import { useNavigate } from 'react-router-dom'
import {getUser} from '../index'
const Navbar = () => {

  const navigate = useNavigate()
  const userInfo = getUser()
  return (
    <>
      <div className='flex text-[#BCC0C4] pb-2 border-b-2 border-[#232530] justify-between'>
        <div className='flex'>
        <img src={process.env.PUBLIC_URL + '/img/logo2.png'} alt='로고사진대체 예정' className='w-24' onClick={()=>{navigate('/')}}/>
        <p className='flex items-center justify-center text-2xl -ml-2'>Predict Bit</p>
        </div>
       
        <div className='flex items-center space-x-4 mr-4'>
        <button onClick={()=>{navigate('/logIn')}}
          className="border-1 rounded-md border-[#232530] bg-transparent uppercase hover:scale-110 hover:text-white hover:bg-blue-500 text-xl">
          LogIn
          </button>
        <button onClick={()=>{navigate('/signUp')}}
          className="border-1 rounded-md border-[#232530] bg-transparent uppercase hover:scale-110 hover:text-white hover:bg-red-500 text-xl">
          SignUp
          </button> <button onClick={()=>{navigate('/trading')}}
          className="border-1 rounded-md border-[#232530] bg-transparent uppercase hover:scale-110 hover:text-white hover:bg-purple-500 text-xl">
          Trading
          </button>
        </div>
           {/* <p
          className='flex items-center'>{localStorage.getItem('nickname')}님 페이지</p> */}
      </div>
    
      </>
  )
}

export default Navbar