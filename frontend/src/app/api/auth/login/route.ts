import { NextRequest, NextResponse } from "next/server"; 

export async function POST(request: NextRequest) {
    const body = await request.json();
    const res = await fetch(`${process.env.BASE_API}/api/v1/auth/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    });

    if(!res.ok) {
        const message = await res.text();
        return new NextResponse(message, { status: res.status });
    }

    const { accessToken, refreshToken } = await res.json();
    const response = new NextResponse(null, { status: 204 });

    if(accessToken) {
        response.cookies.set('accessToken', accessToken, { 
            httpOnly: true, 
            secure: true,
            sameSite: 'lax', 
            path: '/',
            maxAge: 60 * 10,     // 10 minutes
        });
    }
    if(refreshToken) {
        response.cookies.set('refreshToken', refreshToken, { 
            httpOnly: true, 
            secure: true,
            sameSite: 'lax', 
            path: '/',
            maxAge: 60 * 60 * 24 * 7,     // 7 days
        });
    }

    return response;
}